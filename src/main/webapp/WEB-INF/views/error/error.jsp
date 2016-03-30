<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Ooops</title>
    <link href="../css/style_index.css" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/style_index.css" />" rel="stylesheet">

</head>

<body>
<div class="main">
    <div class="content">
        <p class="title"><span class="text"><img src="/resources/images/lib.png" width="76" height="77" hspace="10"
                                                 vspace="10"
                                                 align="middle"></span></p>

        <p class="title">Bookstore</p>

        <p class="text" align="center">
            <a href="http://www.t-systems.ru/career/java-school/1037760">
                Java-school project, T-Systems</a></p>

        <p>&nbsp;</p>

    </div>

    <div class="login_div">

        <c:choose>
            <c:when test="${not empty error}">
                <p class="title">${error}</p>

                <p>${stacktrace}</p>
            </c:when>
            <c:otherwise>
                <p class="title">Something goes wrong... Please try again later!</p>
            </c:otherwise>
        </c:choose>


    </div>

    <div class="footer">
        Developer: Alexander Dvortsov, 2016
    </div>
</div>


</body>
</html>
