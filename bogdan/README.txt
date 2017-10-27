add this resource to context.xml:
<Resource name="jdbc/bogdanDB" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="vasya" password="12345" driverClassName="com.mysql.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/shishkin_bogdan_db"/>
