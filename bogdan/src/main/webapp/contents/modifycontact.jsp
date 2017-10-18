<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="css/addcontact.css" />
<link rel="stylesheet" type="text/css" href="css/modal.css"/>
<script src="js/birthdaySelect.js" type="text/javascript"></script>

<div class="container">`
    <form method="POST" action="contacts" enctype="multipart/form-data">
        <h1>Изменение: контакт №${contact.id}</h1><br>
        <input type="hidden" name="editContactId" value="${contact.id}">
        <h2>Личные данные</h2>
        <div class="form-group">
            <input type="text" name="first_name" id="first_name" value="${contact.first_name}" required autofocus>
            <label class="control-label" for="first_name">Имя</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="last_name" id="last_name" value="${contact.last_name}" required>
            <label class="control-label" for="last_name">Фамилия</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="patronymic" value="${contact.patronymic}" id="patronymic">
            <label class="control-label" for="patronymic">Отчество</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <label class="control-label" for="day" style="font-size: 0.8rem;color: gray;top: -1.2rem;">День</label>
            <select name="day"  id="day" class="date" style="margin-right: 2%;width: 29%;"></select>
            <label class="control-label" for="month" style="left:auto;;">Месяц</label>
            <select name="month" id="month" class="date" style="margin-right: 2%;width: 29%;"></select>
            <label class="control-label" for="year" style="left:auto">Год</label>
            <select name="year" id="year" class="date" style=" width: 29%"></select>
            <input type="hidden" id="hidden_day" value="${contact.birthDate.date}">
            <input type="hidden" id="hidden_month" value="${contact.birthDate.month}">
            <input type="hidden" id="hidden_year" value="${contact.birthDate.year}">
        </div>
        <div class="form-radio gend">
            <div class="radio" id="gender" >
                <c:set var="gender" value="${contact.gender}"/>
                <c:if test="${gender == 'male'}">
                    <c:set var="male" value="checked"/>
                    <c:set var="female" value=" "/>
                </c:if>
                <c:if test="${gender == 'female'}">
                    <c:set var="male" value=" "/>
                    <c:set var="female" value="checked"/>
                </c:if>
                <label>
                    <input type="radio" name="gender" value="male" <c:out value="${male}"/>><i class="helper"></i>Мужской
                </label>
                <label>
                    <input type="radio" name="gender" value="female" <c:out value="${female}"/>><i class="helper"></i>Женский
                </label>
            </div>
            <label class="control-label" for="gender">Пол</label>
        </div>
        <div class="form-group">
            <c:set var="single" value=""/>
            <c:set var="married" value=""/>
            <c:if test="${contact.marital_status == 'married'}">
                <c:set var="married" value="selected"/>
            </c:if>
            <c:if test="${contact.marital_status == 'single'}">
                <c:set var="single" value="selected"/>
            </c:if>
            <select id="select" name="marital" >
                <option value="single" <c:out value="${single}"/>>Не женат / не замужем</option>
                <option value="married" <c:out value="${married}"/>>Женат / замужем</option>
            </select><br>
            <label class="control-label" for="select">Семейное положение</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="url" id="url" value="${contact.website_url}">
            <label class="control-label" for="url">Личный сайт</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="job" name="job" value="${contact.job_place}">
            <label class="control-label" for="job">Место работы</label><i class="bar"></i>
        </div>
        <h2>Контактная информация</h2>
        <div class="form-group">
            <input type="text" id="country" name="country" value="${contact.state}">
            <label class="control-label" for="country">Страна</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="index" name="index" value="${contact.postal_code}">
            <label class="control-label" for="index">Индекс</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="city" name="city" value="${contact.city}">
            <label class="control-label" for="city">Город</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="street" name="street" value="${contact.street}">
            <label class="control-label" for="street">Улица</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="house_number" name="house_number" value="${contact.house_number}">
            <label class="control-label" for="house_number">Дом</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="email" id="email" name="email" value="${contact.email}" required>
            <label class="control-label" for="email">E-mail</label><i class="bar"></i>
        </div>
        <h2>Телефоны</h2>
        <div>
            <table class="list" id="phoneTable">
                <thead>
                <tr>
                    <td width="1" style="text-align: center;"></td>
                    <td class="center image-column ">Код страны</td>
                    <td class="left contact-column ">Код оператора</td>
                    <td class="left model-column ">Номер</td>
                    <td class="left qty-column ">Тип телефона</td>
                    <td class="left price-column ">Комментарий</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${phones}" var="curr">
                    <tr class="contact-row">
                        <td style="text-align: center;"><input type="checkbox" name="p_checkBox" /></td>
                        <td class="center contact-image image-column "><c:out value="${curr.stateCode}"/></td>
                        <td class="left contact-name contact-column"><c:out value="${curr.operatorCode}"/></td>
                        <td class="left contact-model model-column "><c:out value="${curr.number}"/></td>
                        <td class="left quantity qty-column "><c:out value="${curr.type}"/></td>
                        <td class="status left status-column "><c:out value="${curr.comment}"/></td>
                        <input type="hidden" name="id_val" value="${curr.id}"/>
                        <input type="hidden" name="country_code_val" value="${curr.stateCode}"/>
                        <input type="hidden" name="operator_code_val" value="${curr.operatorCode}"/>
                        <input type="hidden" name="number_val" value="${curr.number}"/>
                        <input type="hidden" name="phone_type_val" value="${curr.type}"/>
                        <input type="hidden" name="comment_val" value="${curr.comment}"/>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="form-group gend file_upload">
            <button class="button" type="button" id="myBtn" style="Margin: 0;"><span>Добавить</span></button>
            <label class="control-label" for="myBtn">Новый телефон</label>
            <button class="button" type="button" onclick="deletePhones()" id="deleteBtn" style="Margin: 0;"><span>Удалить</span></button>
        </div>
        <br><br><br>
        <h2>Прикреплённые файлы</h2>
        <div>
            <table class="list" id="fileTable">
                <thead>
                <tr>
                    <td width="1" style="text-align: center;"></td>
                    <td class="center image-column ">Имя</td>
                    <td class="left contact-column ">Комментарий</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${files}" var="curr">
                    <tr class="contact-row">
                        <td style="text-align: center;"><input type="checkbox" name="f_checkBox" /></td>
                        <td class="center contact-image image-column "><c:out value="${curr.name}"/></td>
                        <td class="left contact-name contact-column"><c:out value="${curr.description}"/></td>
                        <input type="hidden" name="t_fid" value="${curr.id}"/>
                        <input type="hidden" name="t_fname" value="${curr.name}"/>
                        <input type="hidden" name="t_frealname" value="${curr.name}${curr.type}"/>
                        <input type="hidden" name="t_fcomment" value="${curr.description}"/>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="form-group gend file_upload">
            <button class="button" type="button" id="addFileBtn" style="Margin: 0;"><span>Добавить</span></button>
            <label class="control-label" for="addFileBtn">Новый файл</label>
            <button class="button" type="button" onclick="deleteFiles()" id="deleteFileBtn" style="Margin: 0;"><span>Удалить</span></button>
        </div>
        <br><br><br>
        <h2>Фото профиля</h2>

        <div class="form-group gend file_upload" id="photoDiv">
            <button id="file_btn" type="button" onclick="document.getElementById('upload_hidden').click();" class="button" style="Margin: 0;"><span>Обзор</span></button>
            <div id="file-name">Файл не выбран</div>
            <input name="photo" id="upload_hidden" accept="image/jpeg,image/gif" style="position: absolute; display: block; overflow: hidden;
				width: 0; height: 0; border: 0; padding: 0;" onchange="document.getElementById('upload_visible').value = this.value;
				document.getElementById('file-name').innerHTML = this.value.slice(12);" type="file">
            <input id="upload_visible" onclick="document.getElementById('upload_hidden').click();" style="height: 0;" type="text" value="${contact.photo.name}${contact.photo.type}">
            <label class="control-label" for="file_btn">Фото</label><i class="bar"></i>
            <output>
                <div id="list" class="image-wrapper">
                    <img id="imgTag" height="150" width="150" src="<c:out value="${contact.photo.relativePath}"/>">
                </div>
            </output>
        </div>
        <div class="form-group">
            <textarea id="comment" rows=1 name="comment"><c:out value="${contact.comment}"/></textarea>
            <label class="control-label" for="comment" >Комментарий...</label><i class="bar"></i>
        </div>
        <div class="button-container">
            <button class="button" type="button" id="submitButton" onclick="clickSubmit();"><span>Сохранить</span></button>
        </div>
        <input type="submit" id="submitInput" style="display: none;"/>
        <input type="hidden" name="command" value="processModifyContact"/>
        <input type="hidden" name="application" value="basic"/>

        <div id="fileModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">&times;</span>
                    <h2>Прикрепить файл</h2>
                </div>
                <div class="modal-body">
                    <div class="container" style="padding: 0 3rem;margin: 3rem 0 0 0;" id="fileContainer">
                        <div class="form-group">
                            <input type="text" id="fname" name="fname"/>
                            <label class="control-label" for="fname" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Имя файла<i class="bar"></i></label>
                        </div>
                        <div class="form-group">
                            <input type="text" id="f_comment" name="fcomment"/>
                            <label class="control-label" for="f_comment" >Комментарий...</label><i class="bar"></i>
                        </div>
                        <div class="button-container" id="buttonContainer">
                            <button id="submitFileBtn" class="button" type="button" onclick="submitFileAdd()"><span>Сохранить</span></button>
                        </div>
                    </div>
                    <div class="modal-footer">

                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</div>


<!-- The Phone Adding Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">&times;</span>
            <h2>Добавить новый телефон</h2>
        </div>
        <div class="modal-body">
            <div class="container" style="padding: 0 3rem;margin: 3rem 0 0 0;">
                <div class="form-group">
                    <label class="control-label" for="country_code" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Код страны</label>
                    <input type="text" style="width: 29%; display: inline" id="country_code" name="country_code" class="onephone" style="margin-right: 2%;width:15%">
                    <label class="control-label" for="operator_code" style="left:auto";>Код оператора</label>
                    <input type="text" style="width: 29%; display: inline;" id="operator_code" name="operator_code" class="onephone" style="margin-right: 2%;width:20%">
                    <label class="control-label" for="number" style="left:auto">Номер телефона</label>
                    <input type="text" style="width: 29%; display: inline" id="number" name="number" class="onephone" style="width:59%">
                </div>
                <div class="form-group">
                    <select id="phone_type" name="phone_type">
                        <option value="not_selected">Не выбрано</option>
                        <option value="work">Рабочий</option>
                        <option value="home">Домашний</option>
                        <option value="mobile">Мобильный</option>
                    </select><br>
                    <label class="control-label" for="phone_type">Тип номера</label><i class="bar"></i>
                </div>
                <div class="form-group">
                    <input type="text" id="p_comment" name="comment"/>
                    <label class="control-label" for="p_comment" >Комментарий...</label><i class="bar"></i>
                </div>
                <div class="button-container">
                    <button id="addPhoneBtn" class="button" type="button" onclick="submitModal()"><span>Сохранить</span></button>
                </div>
            </div>
        </div>
        <div class="modal-footer">

        </div>
    </div>
</div>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/modify.js" type="text/javascript"></script>