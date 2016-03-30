<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <link href="<%=request.getContextPath()%>/resources/css/register.css"
          rel="stylesheet"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>

</head>

<body>


<form method="POST" name="register_form" id="register_form" action="/profile/register_new" class="box login">

    <fieldset class="boxBody">

        <label for="client_user_name">Логин:</label><input name="client_user_name"
                                                           id="client_user_name" type="text"
                                                           value=""
                                                           pattern=".{3,16}"
                                                           required
                                                           title="Логин может содержать от 3 до 255 символов">
        <span style="color: red; float: right" id="error_login"></span>

        <label for="client_password">Пароль:</label><input name="client_password"
                                                           id="client_password" type="password"
                                                           value=""
                                                           pattern=".{3,16}"
                                                           required
                                                           title="Пароль может содержать от 3 до 16 символов">


        <label for="client_name">Имя:</label><input name="client_name"
                                                    id="client_name" type="text"
                                                    value=""
                                                    pattern=".{3,255}"
                                                    required
                                                    title="Имя может содержать от 3 до 255 символов">

        <label for="client_surname">Фамилия:</label><input name="client_surname"
                                                           id="client_surname" type="text"
                                                           value=""
                                                           pattern=".{3,255}"
                                                           required
                                                           title="Фамилия может содержать от 3 до 255 символов">


        <label for="client_address">Адрес:</label><input name="client_address"
                                                         id="client_address" type="text"
                                                         value=""
                                                         pattern=".{5,255}"
                                                         required
                                                         title="Адрес может содержать от 5 до 255 символов">

        <label for="client_email">Почта:</label><input name="client_email"
                                                       id="client_email" type="email"
                                                       value=""
                                                       required
                                                       title="Введите валидный e-mail">

        <label for="client_bday">Дата рождения:</label><input name="client_bday"
                                                              id="client_bday" type="date"
                                                              value=""
                                                              required
                                                              title="Выберите дату вашего рождения">
    </fieldset>

    <footer>
        <input type="submit" class="btnLogin" value="Зарегистрироваться">
    </footer>

</form>

<script>

    function loginUniqValidate() {
        var jqXHR = $.ajax({
            url: 'profile/ajaxLoginUniqValidation',
            data: ({login: $('#client_user_name').val()}),
            async: false,
            success: function (data) {
                if (data.length > 1) {
                    $('#error_login').html(data);
                }
            }
        });
        return jqXHR.responseText < 2;
    }

    $(document).ready(function () {
        $("#register_form").submit(function () {
            var isValidated = loginUniqValidate();
            return isValidated;
        });
    });

</script>


</body>
</html>