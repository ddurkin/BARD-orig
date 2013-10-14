package acl

import bard.acl.CapPermissionInterface
import bard.db.people.Person
import bard.db.people.Role
import bard.db.registration.Assay
import grails.plugins.springsecurity.SpringSecurityService
import grails.util.GrailsNameUtils
import groovy.transform.TypeChecked
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclClass
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclEntry
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclObjectIdentity
import org.codehaus.groovy.grails.plugins.springsecurity.acl.AclSid
import org.grails.plugins.springsecurity.service.acl.AclUtilService
import org.hibernate.Query
import org.hibernate.Session
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.NotFoundException
import org.springframework.security.acls.model.Permission
import org.springframework.security.core.GrantedAuthority
import util.BardUser

class CapPermissionService implements CapPermissionInterface {

    AclUtilService aclUtilService
    SpringSecurityService springSecurityService
    SpringSecurityUiService springSecurityUiService

    void addPermission(domainObjectInstance) {

        //We require that all newly created objects have an ownerRole
        //At this time it is not a constraint in the database, but we should make it a constraint after we
        //back populate this field in all the entities that we can create . At present these entities are
        //Assay,Project,Panel and Experiments
        Role role = domainObjectInstance.ownerRole
        if (!role) {  //Use any team from the user roles, if you don;t find any throw an exception
            final BardUser bardUser = (BardUser) springSecurityService.principal
            //use the first team that you can find
            final Collection<GrantedAuthority> authorities = bardUser.authorities
            if(authorities){
                role = (Role)authorities.get(0);
            }

            if (!role) {
                throw new RuntimeException("Property ownerRole is a required field!!")
            }
        }
        addPermission(domainObjectInstance, role, BasePermission.ADMINISTRATION)
    }



    void addPermission(domainObjectInstance, Role role, Permission permission) {
        aclUtilService.addPermission(domainObjectInstance, role.authority, permission)
    }

    void removePermission(domainObjectInstance) {
        //find the recipient
        final Class<?> clazz = domainObjectInstance.getClass()
        AclClass aclClass = AclClass.findByClassName(clazz.getName())
        final AclObjectIdentity aclObjectIdentity = AclObjectIdentity.findByObjectIdAndAclClass(domainObjectInstance.id, aclClass)

        if (aclObjectIdentity) {
            List<AclEntry> aclEntryList = AclEntry.findAllByAclObjectIdentity(aclObjectIdentity)

            for (AclEntry aclEntry : aclEntryList) {
                final String recipient = aclEntry?.sid?.sid
                if (recipient) {
                    //we're using BasePermission.ADMINISTRATION here because it is deeply inconvenient to obtain the real mask object from
                    //aclEntry
                    aclUtilService.deletePermission(domainObjectInstance, recipient, BasePermission.ADMINISTRATION)
                }
            }
        }
    }
    void updatePermission(domainObjectInstance,Role newRole){
        final Class<?> clazz = domainObjectInstance.getClass()
        AclClass aclClass = AclClass.findByClassName(clazz.getName())
        final AclObjectIdentity aclObjectIdentity = AclObjectIdentity.findByObjectIdAndAclClass(domainObjectInstance.id, aclClass)

        if (aclObjectIdentity) {

            AclSid aclSid = AclSid.findBySidAndPrincipal(newRole.authority, false)
            if (!aclSid) {
                aclSid= new AclSid(sid: newRole.authority, principal: false)
                aclSid.save(flush:true)
            }
            List<AclEntry> aclEntryList = AclEntry.findAllByAclObjectIdentity(aclObjectIdentity)
            for (AclEntry aclEntry : aclEntryList) {
                 springSecurityUiService.updateAclEntry(aclEntry,aclObjectIdentity.id,
                         aclSid.id,aclEntry.aceOrder,aclEntry.mask,aclEntry.granting,aclEntry.auditSuccess,aclEntry.auditFailure)
            }
        }
    }
    /**
     * @param domainObjectInstance a domainInstance we track ACL permissions on
     * @return a String representing the Role name or username that owns this domainInstance
     */
    String getOwner(domainObjectInstance) {
        String owner = "none"

        final Class<?> clazz = domainObjectInstance.getClass()
        AclClass aclClass = AclClass.findByClassName(clazz.getName())
        final AclObjectIdentity aclObjectIdentity = AclObjectIdentity.findByObjectIdAndAclClass(domainObjectInstance.id, aclClass)
        if (aclObjectIdentity) {
            AclEntry aclEntry = AclEntry.findByAclObjectIdentity(aclObjectIdentity)
            AclSid aclSid = aclEntry?.sid
            if (aclSid) {
                if (!aclSid.principal) {
                    Role role = Role.findByAuthority(aclSid.sid)
                    if (role) {
                        owner = role?.displayName
                    } else {
                        owner = aclSid.sid
                    }

                } else {
                    owner = aclSid.sid
                }
            }
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
        if (username) {
            allRolesAndUserName.addAll(SpringSecurityUtils.getPrincipalAuthorities()*.getAuthority())
            allRolesAndUserName.add(username)
            return findAllByRolesAndClass(allRolesAndUserName, domainClass)
        } else {
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
        // a little hack here, to get the table name
        // will break if we start tracking ACLs on domains with non-standard naming
        final String tableName = domainClass.getSimpleName().toLowerCase()
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
