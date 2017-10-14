<%--
  Created by IntelliJ IDEA.
  User: bogda
  Date: 02.10.2017
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page buffer="8000kb"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
    <title>${title}</title>
</head>
<body>
<jsp:include page="${page.header}" flush="true"/>
<div>
    <jsp:include page="${page.body}" flush="true"/>
</div>
<jsp:include page="${page.footer}" flush="true"/>
</body>
</html>
