<%@ page buffer="8000kb"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page errorPage="../contents/error.jsp" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css" />
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
