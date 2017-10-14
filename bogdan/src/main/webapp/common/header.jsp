<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<header>
    <nav>
    <ul class="nav">
        <li><form action = "welcome" method="POST">
            <input type="hidden" name="command" value="viewWelcome"/>
            <input type="hidden" name="application" value="basic"/>
            <input type="submit" value="Главная"/>
        </form>
        </li>
        <li><form action = "add" method="POST">
            <input type="hidden" name="command" value="viewAddContact"/>
            <input type="hidden" name="application" value="basic"/>
            <input type="submit" value="Добавить"/>
        </form></li>
        <li><form action = "contacts" method="POST">
            <input type="hidden" name="command" value="viewContacts"/>
            <input type="hidden" name="application" value="basic"/>
            <input type="submit" value="Контакты"/>
        </form></li>
        <li><a href="#">Поиск</a></li>
        <li><a href="#">О сайте</a></li>
    </ul>
    </nav>
</header>
