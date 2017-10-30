<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="css/addcontact.css" type="text/css" rel="stylesheet"/>

<div class="container">
    <h1>Поиск по критериям</h1>
    <form method="post" action="contacts">
        <h2>Личные данные</h2>
        <div class="form-group">
            <input type="text" name="first_name" id="first_name">
            <label class="control-label" for="first_name">Имя</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="last_name" id="last_name">
            <label class="control-label" for="last_name">Фамилия</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="patronymic" id="patronymic">
            <label class="control-label" for="patronymic">Отчество</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <div class="form-radio gend">
                <div class="radio" id="gender" >
                    <label>
                        <input type="radio" name="gender" value="male"><i class="helper"></i>Мужской
                    </label>
                    <label>
                        <input type="radio" name="gender" value="female"><i class="helper"></i>Женский
                    </label>
                    <label>
                        <input type="radio" name="gender" value="" checked><i class="helper"></i>Любой
                    </label>
                </div>
                <label class="control-label" for="gender">Пол</label>
            </div>
        </div>
        <div class="form-group">
            <select id="select" name="marital" >
                <option value="single" selected>Не женат / не замужем</option>
                <option value="married">Женат / замужем</option>
                <option value="" selected="selected">Не выбрано</option>
            </select><br>
            <label class="control-label" for="select">Семейное положение</label><i class="bar"></i>
        </div>
        <h3>Дата рождения</h3>
        <h4>От: </h4>
        <div class="form-group" id="less_date">
            <div class="thin"> <label class="control-label" for="less_day" style="font-size: 0.8rem;color: gray;top: -1.2rem;">День</label>
                <input type="text" name="less_day" id="less_day">
                <i class="bar"></i></div>
            <div class="thin"> <label class="control-label" for="less_month" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Месяц</label>
                <input type="text" name="less_month" id="less_month">
                <i class="bar"></i></div>
            <div class="thin"> <label class="control-label" for="less_year" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Год</label>
                <input type="text" name="less_year" id="less_year">
                <i class="bar"></i></div>
        </div>
        <h4>До: </h4>
        <div class="form-group" id="more_date">
            <div class="thin"> <label class="control-label" for="more_day" style="font-size: 0.8rem;color: gray;top: -1.2rem;">День</label>
                <input type="text" name="more_day" id="more_day">
                <i class="bar"></i></div>
            <div class="thin"> <label class="control-label" for="more_month" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Месяц</label>
                <input type="text" name="more_month" id="more_month">
                <i class="bar"></i></div>
            <div class="thin"> <label class="control-label" for="more_year" style="font-size: 0.8rem;color: gray;top: -1.2rem;">Год</label>
                <input type="text" name="more_year" id="more_year">
                <i class="bar"></i></div>
        </div>
        <h2>Контактная информация</h2>
        <div class="form-group">
            <input type="text" id="country" name="country" >
            <label class="control-label" for="country">Страна</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="index" name="index" >
            <label class="control-label" for="index">Индекс</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="city" name="city" >
            <label class="control-label" for="city">Город</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="street" name="street" >
            <label class="control-label" for="street">Улица</label><i class="bar"></i>
        </div>
        <div class="form-group">
            <input type="text" id="house_number" name="house_number" >
            <label class="control-label" for="house_number">Дом</label><i class="bar"></i>
        </div>
        <div class="button-container" id="buttonContainer">
            <input type="hidden" name="command" value="processSearchContacts">
            <input type="hidden" name="application" value="basic">
            <button id="searchButton" class="button" type="button" onclick="submitSearch()"><span>Поиск</span></button>
            <input type="submit" id="submit" style="display: none">
        </div>
    </form>
</div>
<script type="text/javascript" src="js/search.js"></script>