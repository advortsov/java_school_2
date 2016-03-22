<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<jsp:useBean id="currentClient" class="com.tsystems.javaschool.dao.entity.Client" scope="page"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
    //    currentClient = (Client) session.getAttribute("currentClient");
//    if (currentClient == null) {
//        session.invalidate();
//    }
//
//    OrderManager orderManager1 = Managers.getOrderManager();
//
//    List<Order> orders = orderManager1.loadAllOrders();
%>


<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabs.css" type="text/css"/>--%>

<div class="menu1">
    <br id="tab2"/>
    <br id="tab3"/>
    <br id="tab4"/>
    <br id="tab5"/>
    <br id="tab6"/>
    <br id="tab7"/>
    <a href="#tab1">Книги</a>
    <a href="#tab2">Жанры</a>
    <a href="#tab3">Издатели</a>
    <a href="#tab4">Авторы</a>
    <a href="#tab5">Заказы</a>
    <a href="#tab6">Топ-10</a>
    <a href="#tab7">Выручка</a>

    <%--tab1 - Книги--%>
    <%@include file="../jspf/adminf/add_book.jspf" %>

    <%--tab2 - Жанры--%>
    <%@include file="../jspf/adminf/add_genre.jspf" %>

    <%--tab3 - Издатели--%>
    <%@include file="../jspf/adminf/add_publisher.jspf" %>

    <%--tab4 - Авторы--%>
    <%@include file="../jspf/adminf/add_author.jspf" %>

    <%--tab5 - Заказы--%>
    <%@include file="../jspf/adminf/all_orders.jspf" %>

    <%--tab6 - top10--%>
    <%@include file="../jspf/adminf/top10.jspf" %>

    <%--tab7 - Proceeds--%>
    <%@include file="../jspf/adminf/proceeds_per_period.jspf" %>

</div>
<%@include file="../jspf/footer.jspf" %>
