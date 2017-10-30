<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="custom" uri="/WEB-INF/functions" %>

<link rel="stylesheet" type="text/css" href="css/addcontact.css"/>
<link rel="stylesheet" type="text/css" href="css/modal.css"/>

<form method="post">
    <div class="container">
        <h1>Отправка писем</h1><br>
        <h2>Выбранные ящики</h2>
        <div id="emails">
            <c:forEach items="${rows}" var="curr">
                <div class="form-group">
                    <input type="email" name="emailadresses" id = "asd" value="${curr.contact.email}">
                    <label class="control-label" for="asd">Email</label>
                    <i class="bar"></i>
                </div>
            </c:forEach>
        </div>
        <button class="button" type="button" id="addButton" onclick="addMailField()">
            <span>Добавить</span>
        </button>
        <h2>Тема</h2>
        <div class="form-group">
            <input type="text" id="topic" name="topic">
            <label class="control-label" for="topic">Тема</label><i class="bar"></i>
        </div>
        <h2>Шаблон</h2>
        <div class="form-group">
            <select id="select" name="template" onchange="onChangeSelect()" style="width: auto">
                <option selected>Без шаблона</option>
                <c:forEach items="${templates}" var="curr">
                    <option value="${curr.name}">${custom:getTemplateName(curr.name)}</option>
                </c:forEach>
            </select><br>
            <button type="button" class="button" id="modalButton"
                    onclick="showModalPreview()"><span>Превью</span></button>
            <c:forEach items="${templates}" var="curr">
                <input type="hidden" id="${curr.name}" value="${custom:getTemplateSample(curr.name)}">
            </c:forEach>
            <label class="control-label" for="select">Шаблон</label><i class="bar"></i>
        </div>
        <h2>Текст</h2>
        <div class="form-group">
            <textarea id="text" rows=10 name="text"></textarea>
            <label class="control-label" for="text">Текст</label><i class="bar"></i>
        </div>
        <div class="button-container">
            <button class="button" type="button" id="submitButton" onclick="clickSend();"><span>Отправить</span>
            </button>
            <input id="submitInput" type="submit" value="submit" style="display:none;">
            <input type="hidden" name="command" value="processSendMail"/>
        </div>


        <div id="previewModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content" style="width: fit-content;">
                <div class="modal-header">
                    <span id="close" class="close">&times;</span>
                    <h2>Превью сообщения</h2>
                </div>
                <div class="modal-body" style="width:400px;">
                    <div class="container" style="padding: 0 3rem;margin: 3rem 0 0 0; width: fit-content">
                        <div class="form-group">
                            <p id="templateText"></p>
                            <label class="control-label" for="templateText"
                                   style="font-size: 0.8rem;color: gray;top: -1.2rem;">Шаблон сообщения</label>
                        </div>
                        <div class="button-container">
                            <button id="okButton" class="button" type="button" onclick="closeModal()">
                                <span>Я понял</span></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="js/send.js"></script>
<script type="text/javascript" src="js/send.js"></script>