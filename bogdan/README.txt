add this resource to context.xml:
<Resource name="jdbc/bogdanDB" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="root" password="root" driverClassName="com.mysql.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/shishkin_bogdan_db"/>

Про то, что нельзя по root подключаться, знаю

Сидел и доделывал table.jsp, но не успел немного. Глянь заодно, что там не так)
Атрибут rows я кладу в классе ShowContactsViewHelper