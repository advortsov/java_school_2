<%@ page import="com.tsystems.javaschool.services.enums.OrderStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<div class="edit_penal">
    <br><strong>Изменение статуса заказа</strong>

    <form name="order_set_status_form" action="/admin/edit_order" method="post">
        <div class="edit_book_info">
            <div>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css"/>

                <div class="field">
                    <label for="id">ID заказа:</label> ${order.id}
                    <input type="text" value="${order.id}"
                           name="id" id="id" hidden>
                </div>

                <div class="field">
                    <label for="order_status">Статус выполнения заказа:</label>


                    <select name="order_status" id="order_status">
                        <c:forEach items="${orderStatusList}" var="status">
                            <option>${status}</option>
                        </c:forEach>
                    </select>
                </div>
                <%--<input type="hidden" name="action" value="set"></p>--%>
                <p><input type="submit" value="Задать"></p>
            </div>
        </div>
    </form>
</div>