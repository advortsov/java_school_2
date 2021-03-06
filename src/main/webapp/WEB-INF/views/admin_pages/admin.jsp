<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<jsp:useBean id="currentClient" class="com.tsystems.javaschool.dao.entity.Client" scope="page"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="menu1">
    <br id="tab2"/>
    <br id="tab3"/>
    <br id="tab4"/>
    <br id="tab5"/>
    <br id="tab6"/>
    <br id="tab7"/>
    <a href="#tab1">Books</a>
    <a href="#tab2">Genres</a>
    <a href="#tab3">Publishers</a>
    <a href="#tab4">Authors</a>
    <a href="#tab5">Orders</a>
    <a href="#tab6">Top-10</a>
    <a href="#tab7">Proceed</a>

    <%--tab1 - Books--%>
    <%@include file="../jspf/adminf/add_book.jspf" %>

    <%--tab2 - Genres--%>
    <%@include file="../jspf/adminf/add_genre.jspf" %>

    <%--tab3 - Publishers--%>
    <%@include file="../jspf/adminf/add_publisher.jspf" %>

    <%--tab4 - Authors--%>
    <%@include file="../jspf/adminf/add_author.jspf" %>

    <%--tab5 - Orders--%>
    <%@include file="../jspf/adminf/all_orders.jspf" %>

    <%--tab6 - top10--%>
    <%@include file="../jspf/adminf/top10.jspf" %>

    <%--tab7 - Proceed--%>
    <%@include file="../jspf/adminf/proceeds_per_period.jspf" %>

</div>
<%@include file="../jspf/footer.jspf" %>
