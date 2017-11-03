Tomcat configuration:
1)
add the following tags to settings.xml within <servers> tag of your local tomcat instance,
if you want to use tomcat7 maven plugin:
<server>
      <id>Tomcat7Server</id>
      <username>admin</username>
      <password>1234568</password>
</server>

2)
add this to tomcat-users.xml:
 <role rolename="admin"/>
<role rolename="admin-gui"/>
<role rolename="manager-gui"/>
<role rolename="manager-jmx"/>
<role rolename="manager-script"/>
<role rolename="manager-status"/>
<user username="admin" password="admin" roles="admin,admin-gui,manager-status,manager-gui,manager-jmx,manager-script"/>

3) add this resource to context.xml or create a new context.xml file within META-INF folder and add to it:

<Resource name="jdbc/bogdanDB" auth="Container" type="javax.sql.DataSource"
              maxTotal="100" maxIdle="30" maxWaitMillis="10000"
              username="vasya" password="vasya" driverClassName="com.mysql.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/shishkin_bogdan_db"/>