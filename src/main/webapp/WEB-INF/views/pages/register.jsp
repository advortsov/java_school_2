<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <link href="<%=request.getContextPath()%>/resources/css/register.css" rel="stylesheet"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>

</head>

<body>


<form method="POST" name="register_form" id="register_form" action="/reg/register_new" class="box login">

    <fieldset class="boxBody">

        <label for="client_user_name">Login:</label><input name="client_user_name"
                                                           id="client_user_name" type="text"
                                                           value=""
                                                           pattern=".{3,16}"
                                                           required
                                                           title="Логин может содержать от 3 до 255 символов">

        <label for="client_password">Password:</label><input name="client_password"
                                                             id="client_password" type="password"
                                                             value=""
                                                             pattern=".{3,16}"
                                                             required
                                                             title="Пароль может содержать от 3 до 16 символов">


        <label for="client_name">Name:</label><input name="client_name"
                                                     id="client_name" type="text"
                                                     value=""
                                                     pattern=".{3,255}"
                                                     required
                                                     title="Name может содержать от 3 до 255 символов">

        <label for="client_surname">Surname:</label><input name="client_surname"
                                                           id="client_surname" type="text"
                                                           value=""
                                                           pattern=".{3,255}"
                                                           required
                                                           title="Surname может содержать от 3 до 255 символов">


        <label for="client_address">Address:</label><input name="client_address"
                                                           id="client_address" type="text"
                                                           value=""
                                                           pattern=".{5,255}"
                                                           required
                                                           title="Address может содержать от 5 до 255 символов">

        <label for="client_email">Email:</label><input name="client_email"
                                                       id="client_email" type="email"
                                                       value=""
                                                       required
                                                       title="Введите валидный e-mail">

        <label for="client_bday">Address:</label><input name="client_bday"
                                                     id="client_bday" type="date"
                                                     value=""
                                                     required
                                                     title="Выберите дату вашего рождения">

        <div style="color: red; float: right" name="error_login_name" id="error_login_name"></div>

    </fieldset>

    <footer>
        <input type="submit" class="btnLogin" value="Register">
    </footer>

</form>

<script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">

    function loginUniqValidate() {
        var jqXHR = $.ajax({
            url: '/reg/ajaxLoginUniqValidation',
            data: ({userLogin: $('#client_user_name').val()}),
            async: false,
            success: function (data) {
                if (data.length > 1) {
                    $('#error_login_name').html(data);
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