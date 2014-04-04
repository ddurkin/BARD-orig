set -ex
export DEBIAN_FRONTEND='noninteractive' 
#apt-get update -y
#apt-get install -y openjdk-7-jdk 
#apt-get install -yq tomcat7 redis-server

/etc/init.d/tomcat7 stop

rm -r /var/log/tomcat7
mkdir -p /var/log/tomcat7

mkdir -p ~tomcat7/bard-results
mkdir -p ~tomcat7/logs

mkdir -p ~tomcat7/.grails/BARD
cp /vagrant/BARD-production-config.groovy ~tomcat7/.grails/BARD
mkdir -p ~tomcat7/.grails/dataExport
cp /vagrant/dataExport-production-config.groovy ~tomcat7/.grails/dataExport

cp /vagrant/tomcat7 /etc/default/tomcat7
rm -rf /var/lib/tomcat7/webapps/BARD*
rm -rf /var/lib/tomcat7/webapps/dataExport*
rm -rf /var/lib/tomcat7/webapps/external-ontology-proxy*
cp /vagrant/wars/BARD.war /var/lib/tomcat7/webapps
cp /vagrant/wars/dataExport.war /var/lib/tomcat7/webapps
cp /vagrant/wars/external-ontology-proxy.war /var/lib/tomcat7/webapps

chown -R tomcat7:tomcat7 ~tomcat7/.grails ~tomcat7/logs /var/log/tomcat7 /var/lib/tomcat7/webapps

rm -rf ~tomcat7/schema-copy
mkdir -p ~tomcat7/schema-copy
cp -r /vagrant/schema-copy ~tomcat7
chown -R tomcat7:tomcat7 ~tomcat7/schema-copy

cd ~tomcat7/schema-copy
./gradlew dependencies

#/etc/init.d/tomcat7 start
