<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="custom" uri="/WEB-INF/functions" %>

<link rel="stylesheet" type="text/css" href="css/addcontact.css" />
<form method="post">
<div class="container">
    <h1>Отправка писем</h1><br>
    <h2>Выбранные ящики</h2>
    <c:forEach items="${rows}" var="curr">
        <div>
            <input type="text" name="emails" value="${curr.contact.email}">
            <input type="hidden" name="ids" value="${curr.contact.id}">
        </div>
    </c:forEach>
    <h2>Тема</h2>
    <div class="form-group">
        <input type="text" id="topic" name="topic" >
        <label class="control-label" for="topic">Тема</label><i class="bar"></i>
    </div>
    <h2>Шаблон</h2>
    <div class="form-group">
        <select id="select" name="template">
            <option selected>Без шаблона</option>
            <c:forEach items="${templates}" var="curr">
                <option value="${curr.name}">${custom:getTemplateName(curr.name)}</option>
            </c:forEach>
        </select><br>
        <label class="control-label" for="select">Шаблон</label><i class="bar"></i>
    </div>
    <h2>Текст</h2>
    <div class="form-group">
        <textarea id="text" rows=10 name="text"></textarea>
        <label class="control-label" for="text" >Текст</label><i class="bar"></i>
    </div>
    <div class="button-container">
        <button class="button" type="button" id="submitButton" onclick="clickSend();"><span>Отправить</span></button>
        <input id="submitInput" type="submit" value="submit" style="display:none;">
        <input type="hidden" name="command" value="processSendMail"/>
    </div>
</div>
</form>
<script type="text/javascript" src="js/send.js"></script>