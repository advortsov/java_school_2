<%@ page import="com.tsystems.javaschool.dao.entity.Client" %>
<%@ page import="com.tsystems.javaschool.dao.entity.Order" %>
<%@ page import="com.tsystems.javaschool.services.util.Managers" %>
<%@ page import="com.tsystems.javaschool.view.controllers.ClientController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="/WEB-INF/jspf/left_menu.jspf" %>

<jsp:useBean id="clientManager" class="com.tsystems.javaschool.services.impl.ClientManagerImpl" scope="page"/>
<jsp:useBean id="publisherManager" class="com.tsystems.javaschool.services.impl.PublisherManagerImpl" scope="page"/>
<jsp:useBean id="authorManager" class="com.tsystems.javaschool.services.impl.AuthorManagerImpl" scope="page"/>

<div class="edit_penal">

    <br><strong>Личные данные</strong>

    <%
        Client currClient = (Client) session.getAttribute("currentClient");
        if (currClient == null || currClient.getName().equals("Guest")) {

            String currClientName;

            if (request.getUserPrincipal().getName().toString() != null) {
                currClientName = request.getUserPrincipal().getName().toString();
            } else {
                currClientName = (String) session.getAttribute("username");
            }
            currClient = ClientController.actualizeClient(request, currClientName);
        }
    %>

    <p>&nbsp;</p>

    <form name="client_edit_form" action="/editProfile" method="post">

        <div class="edit_book_info">
            <div>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css"/>

                <div class="field">
                    <label for="client_name">Имя:</label><input name="client_name"
                                                                id="client_name" type="text"
                                                                value="<%=currClient.getName()%>"
                                                                pattern=".{5,255}"
                                                                required
                                                                title="Имя может содержать от 5 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_surname">Фамилия:</label><input name="client_surname"
                                                                       id="client_surname" type="text"
                                                                       value="<%=currClient.getSurname()%>"
                                                                       pattern=".{5,255}"
                                                                       required
                                                                       title="Фамилия может содержать от 5 до 255 символов">
                </div>

                <div class="field">

                    <label for="client_address">Адрес:</label><input name="client_address"
                                                                     id="client_address" type="text"
                                                                     value="<%=currClient.getAddress()%>"
                                                                     pattern=".{5,255}"
                                                                     required
                                                                     title="Адрес может содержать от 5 до 255 символов">
                </div>
                <div class="field">

                    <label for="client_bday">Дата рождения:</label><input name="client_bday"
                                                                          id="client_bday" type="date"
                                                                          value="<%=currClient.getBirthday()%>"
                                                                          required
                                                                          title="Выберите дату вашего рождения">
                </div>

                <div class="field">

                    <label for="client_email">Почта:</label><input name="client_email"
                                                                   id="client_email" type="email"
                                                                   value="<%=currClient.getEmail()%>"
                                                                   required
                                                                   title="Введите валидный e-mail">
                </div>

                <input type="hidden" name="action" value="edit"></p>
                <p><input type="submit" value="Изменить"></p>
            </div>
        </div>

    </form>
    <%--copypast--%>


    <div class="client_orders_penal">
        <%
            List<Order> clientOrders = new ArrayList<>();
            if (request.isUserInRole("user") || request.isUserInRole("admin")) {
                clientOrders = Managers.getClientManager().getClientOrders(currClient);
            }
            if (!clientOrders.isEmpty()) {
        %>

        <br><strong>Ваши заказы</strong>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order_table.css" type="text/css"/>
        <table>
            <%--<table border="1">--%>
            <tr>
                <td>id</td>
                <td>Дата</td>
                <td>Статус заказа</td>
                <td>Способ доставки</td>
                <td>Способ оплаты</td>
                <td>Статус оплаты</td>
                <td>Сумма заказа</td>
            </tr>
            <%
                for (Order order : clientOrders) {
            %>
            <tr>
                <td><%=order.getId()%>
                </td>
                <td><%=order.getDate()%>
                </td>
                <td><%=order.getOrderStatus()%>
                </td>
                <td><%=order.getShippingType()%>
                </td>
                <td><%=order.getPaymentType()%>
                </td>
                <td><%=order.getPaymentStatus()%>
                </td>
                <td><%=Managers.getOrderManager().orderTotalSumm(order)%> руб.</td>

            </tr>
            <%
                }
            %>
        </table>

        <%
        } else {
        %>
        <br><strong>У Вас пока нет заказов</strong>
        <%
            }
        %>

    </div>

</div>