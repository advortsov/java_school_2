<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <link href="<%=request.getContextPath()%>/resources/css/home.css"
          rel="stylesheet"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>

</head>

<body>


<form method="POST"
      action="<%=request.getContextPath()%>/j_spring_security_check"
      class="box login">

    <fieldset class="boxBody">
        <label>Username </label> <input type='text' name='user_login' value=''>
        <label>Password </label> <input type='password' name='password_login'/>

        <h6><a href="/books">I'm guest</a></h6>
        <h6><a href="reg/register">Register</a></h6>

        <c:if test="${not empty error}">
            <div class="error" style="text-align:right;">${error}</div>
        </c:if>
    </fieldset>

    <footer>
        <input type="submit" class="btnLogin" value="Вход">
    </footer>

</form>


</body>
</html>