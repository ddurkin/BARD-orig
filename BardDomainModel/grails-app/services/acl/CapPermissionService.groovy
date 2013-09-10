package acl

import bard.acl.CapPermissionInterface
import bard.db.people.Person
import bard.db.people.Role
import bard.db.registration.Assay
import grails.plugins.springsecurity.SpringSecurityService
import groovy.transform.TypeChecked
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclEntry
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclObjectIdentity
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclSid
import org.grails.plugins.springsecurity.service.acl.AclUtilService
import org.hibernate.Query
import org.hibernate.Session
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.NotFoundException
import org.springframework.security.acls.model.Permission

class CapPermissionService implements CapPermissionInterface {

    AclUtilService aclUtilService
    SpringSecurityService springSecurityService

    void addPermission(domainObjectInstance) {
        String userName = springSecurityService.principal?.username
        Person person = Person.findByUserName(userName)

        //we would use a default role so that all of our tests can pass
        //Take this out as soon as we complete https://www.pivotaltracker.com/story/show/51238251
        Role newObjectRole = person?.newObjectRole ?: new Role(authority: userName)
        //we assume that the newObjectRole should never be null. There will be a check constraint to insure that
        addPermission(domainObjectInstance, newObjectRole, BasePermission.ADMINISTRATION)
    }

    void addPermission(domainObjectInstance, Role role, Permission permission) {
        aclUtilService.addPermission(domainObjectInstance, role.authority, permission)
    }

    void removePermission(domainObjectInstance) {
        //find the recipient

        final AclObjectIdentity aclObjectIdentity = AclObjectIdentity.findByObjectId(domainObjectInstance.id)
        if (aclObjectIdentity) {
            AclEntry aclEntry = AclEntry.findByAclObjectIdentity(aclObjectIdentity)
            final String recipient = aclEntry?.sid?.sid
            if (recipient) {
                aclUtilService.deletePermission(domainObjectInstance, recipient, BasePermission.ADMINISTRATION)
            }
        }
    }
           //TODO ensure that domainClass is passed in and used correctly as Ids overlap
    String getOwner(domainObjectInstance) {
        String owner = "none"
        try {
            final AclObjectIdentity aclObjectIdentity = AclObjectIdentity.findByObjectIdAndAclClass(domainObjectInstance.id, domainObjectInstance.getClass().getName())
            if (aclObjectIdentity) {
                AclEntry aclEntry = AclEntry.findByAclObjectIdentity(aclObjectIdentity)
                AclSid aclSid = aclEntry?.sid
                if (aclSid) {
                    if (!aclSid.principal) {
                        Role role = Role.findByAuthority(aclSid.sid)
                        owner = role?.displayName
                    } else {
                        owner = aclSid.sid
                    }
                }
            }
        }
        catch (NotFoundException nfe) {

        }
        return owner
    }

    /**
     *
     * Given a domain Class FindAll the Projects, Assays, or Experiments any domain we're tracking ACL permissions on
     * that a user directly owns or has access to via the roles they're
     * associated with
     * @param domainClass
     * @return List of domains of type T where T is expected to be a Domain Class that we track ACL permissions on
     */
    public <T> List<T> findAllObjectsForRoles(Class<T> domainClass) {
        final String username = springSecurityService.principal?.username
        List<String> allRolesAndUserName = []
        if(username){
            allRolesAndUserName.addAll(SpringSecurityUtils.getPrincipalAuthorities()*.getAuthority())
            allRolesAndUserName.add(username)
            return findAllByRolesAndClass(allRolesAndUserName, domainClass)
        }
        else{
            return []
        }
    }
    /**
     *
     * FindAll the Projects Assays or Experiments that a user directly owns or has access to via the roles they're
     * associated with
     *
     * @param roleNames list or role names (may also include username )
     * @return List of Assays that the list of roles has permissions for
     */
    private <T> List<T> findAllByRolesAndClass(List<String> roleNames, Class<T> domainClass) {
       String tableName = domainClass.getSimpleName().toLowerCase()
        Assay.withSession { Session session ->
            Query query = session.createSQLQuery("""
                        select a.*
                        from ${tableName} a
                            join acl_object_identity acl_oi on acl_oi.object_id_identity = a.${tableName}_id
                            join acl_class acl_c on acl_c.id = acl_oi.object_id_class
                            join acl_entry acl_e on acl_e.acl_object_identity = acl_oi.id
                            join acl_sid sid on sid.id  = acl_e.sid
                        where acl_c.class = :className
                        and sid.sid in (:roleNames)
                        order by a.date_created desc
                    """)
            query.addEntity(domainClass)
            query.setString('className', domainClass.getName())
            query.setParameterList('roleNames', roleNames)
            query.setReadOnly(true)
            return query.list()

        }
    }
}
