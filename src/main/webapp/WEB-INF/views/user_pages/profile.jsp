<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="/WEB-INF/views/jspf/header.jspf" %>
<%@include file="/WEB-INF/views/jspf/left_menu.jspf" %>

<div class="edit_penal">

    <br><strong>Personal data</strong>

    <p>&nbsp;</p>

    <form name="client_edit_form" action="/profile/editProfile" method="post">

        <div class="edit_book_info">
            <div>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css"/>

                <div class="field">
                    <label for="client_name">Name:</label><input name="client_name"
                                                                 id="client_name" type="text"
                                                                 value="${client.name}"
                                                                 pattern=".{3,255}"
                                                                 required
                                                                 title="Name может содержать от 3 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_surname">Surname:</label><input name="client_surname"
                                                                       id="client_surname" type="text"
                                                                       value="${client.surname}"
                                                                       pattern=".{3,255}"
                                                                       required
                                                                       title="Surname может содержать от 3 до 255 символов">
                </div>

                <div class="field">

                    <label for="client_address">Address:</label><input name="client_address"
                                                                       id="client_address" type="text"
                                                                       value="${client.address}"
                                                                       pattern=".{5,255}"
                                                                       required
                                                                       title="Address может содержать от 5 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_bday">Born:</label><input name="client_bday"
                                                                 id="client_bday" type="date"
                                                                 value="${client.birthday}"
                                                                 required
                                                                 title="Выберите дату вашего рождения">
                </div>

                <div class="field">

                    <label for="client_email">Email:</label><input name="client_email"
                                                                   id="client_email" type="email"
                                                                   value="${client.email}"
                                                                   required
                                                                   title="Введите валидный e-mail">
                </div>

                <p><input type="submit" value="Set"></p>
            </div>
        </div>

    </form>


    <div class="client_orders_penal">
        <c:choose>
            <c:when test="${not empty clientOrdersList}">
                <br><strong>Orders</strong>

                <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_table.css" type="text/css"/>--%>

                <table border="1">
                    <tr>
                        <td>id</td>
                        <td>Date</td>
                        <td>Client name</td>
                        <td>Order status</td>
                        <td>Shipping type</td>
                        <td>Payment type</td>
                        <td>Payment status</td>
                        <td>Total</td>
                    </tr>
                    <c:forEach items="${clientOrdersList}" var="order">
                        <td>${order.id}</td>
                        <td>${order.date}</td>
                        <td>${order.client.name}</td>
                        <td>${order.orderStatus}</td>
                        <td>${order.shippingType}</td>
                        <td>${order.paymentType}</td>
                        <td>${order.paymentStatus}</td>
                        <td>${order.totalSumm}</td>

                        <c:choose>
                            <c:when test="${isAdmin}">
                                <td>
                                    <a href="admin/edit_order?id=${order.id}">
                                        <img src="/resources/images/edit.png" alt="Editing" name="edit"/></a>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${order.orderStatus eq 'DELIVERED'}">
                                <td>
                                    <a href="/order/repeatOrder?id=${order.id}">
                                        <img src="/resources/images/repeat.png" alt="Repeat order" name="repeat"/></a>
                                </td>
                            </c:when>
                        </c:choose>

                        </tr>
                    </c:forEach>

                </table>
            </c:when>
            <c:otherwise>
                <br><strong>В базе нет заказов</strong>
            </c:otherwise>
        </c:choose>

    </div>

</div>

<%@include file="/WEB-INF/views/jspf/footer.jspf" %>
