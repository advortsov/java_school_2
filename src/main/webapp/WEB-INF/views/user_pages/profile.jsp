<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="/WEB-INF/views/jspf/header.jspf" %>
<%@include file="/WEB-INF/views/jspf/left_menu.jspf" %>

<%--<jsp:useBean id="clientManager" class="com.tsystems.javaschool.services.impl.ClientManagerImpl" scope="page"/>--%>
<%--<jsp:useBean id="publisherManager" class="com.tsystems.javaschool.services.impl.PublisherManagerImpl" scope="page"/>--%>
<%--<jsp:useBean id="authorManager" class="com.tsystems.javaschool.services.impl.AuthorManagerImpl" scope="page"/>--%>

<div class="edit_penal">

    <br><strong>Личные данные</strong>

    <p>&nbsp;</p>

    <form name="client_edit_form" action="/profile/editProfile" method="post">

        <div class="edit_book_info">
            <div>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css"/>

                <div class="field">
                    <label for="client_name">Имя:</label><input name="client_name"
                                                                id="client_name" type="text"
                                                                value="${client.name}"
                                                                pattern=".{3,255}"
                                                                required
                                                                title="Имя может содержать от 3 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_surname">Фамилия:</label><input name="client_surname"
                                                                       id="client_surname" type="text"
                                                                       value="${client.surname}"
                                                                       pattern=".{3,255}"
                                                                       required
                                                                       title="Фамилия может содержать от 3 до 255 символов">
                </div>

                <div class="field">

                    <label for="client_address">Адрес:</label><input name="client_address"
                                                                     id="client_address" type="text"
                                                                     value="${client.address}"
                                                                     pattern=".{5,255}"
                                                                     required
                                                                     title="Адрес может содержать от 5 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_bday">Дата рождения:</label><input name="client_bday"
                                                                          id="client_bday" type="date"
                                                                          value="${client.birthday}"
                                                                          required
                                                                          title="Выберите дату вашего рождения">
                </div>

                <div class="field">

                    <label for="client_email">Почта:</label><input name="client_email"
                                                                   id="client_email" type="email"
                                                                   value="${client.email}"
                                                                   required
                                                                   title="Введите валидный e-mail">
                </div>

                <p><input type="submit" value="Изменить"></p>
            </div>
        </div>

    </form>


    <div class="client_orders_penal">
        <c:choose>
            <c:when test="${not empty clientOrdersList}">
                <br><strong>Заказы</strong>

                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_table.css" type="text/css"/>

                <table border="1">
                    <tr>
                        <td>id</td>
                        <td>Дата</td>
                        <td>Имя клиента</td>
                        <td>Статус заказа</td>
                        <td>Способ доставки</td>
                        <td>Способ оплаты</td>
                        <td>Статус оплаты</td>
                        <td>Сумма заказа</td>
                    </tr>
                    <c:forEach items="${clientOrdersList}" var="order">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.date}</td>
                            <td>${order.client.name}</td>
                            <td>${order.orderStatus}</td>
                            <td>${order.shippingType}</td>
                            <td>${order.paymentType}</td>
                            <td>${order.paymentStatus}</td>
                            <td>${order.totalSumm}</td>
                                <%--логика находится в классе Order (что делать?)--%>
                            <c:choose>
                                <c:when test="${isAdmin}">
                                    <td>
                                        <a href="admin/edit_order?id=${order.id}">
                                            <img src="/resources/images/edit.png" alt="Редактировать" name="edit"/></a>
                                    </td>
                                </c:when>
                            </c:choose>
                            <td>
                                <a href="/order/repeatOrder?id=${order.id}">
                                    <img src="/resources/images/repeat.png" alt="Повторить заказ" name="repeat"/></a>
                            </td>


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
