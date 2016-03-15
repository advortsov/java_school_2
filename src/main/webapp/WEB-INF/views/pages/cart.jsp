<%@ page import="com.tsystems.javaschool.dao.entity.Client" %>
<%@ page import="com.tsystems.javaschool.dao.entity.OrderLine" %>
<%@ page import="com.tsystems.javaschool.services.ShoppingCart" %>
<%@ page import="com.tsystems.javaschool.services.enums.PaymentType" %>
<%@ page import="com.tsystems.javaschool.services.enums.ShippingType" %>
<%@ page import="com.tsystems.javaschool.view.controllers.ClientController" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/left_menu.jspf" %>

<jsp:useBean id="bookManager" class="com.tsystems.javaschool.services.impl.BookManagerImpl" scope="page"/>
<jsp:useBean id="shoppingCartManager" class="com.tsystems.javaschool.services.impl.ShoppingCartManagerImpl"
             scope="session"/>
<jsp:useBean id="currentClient" class="com.tsystems.javaschool.dao.entity.Client" scope="page"/>
<jsp:useBean id="orderLines1" class="java.util.ArrayList" scope="session"/>

<%
    currentClient = (Client) session.getAttribute("currentClient");
    if (currentClient == null) {
        ClientController.actualizeClient(request, userName);
    }

    List<OrderLine> orderLines = null;

    if (request.getSession().getAttribute("cart") == null) {
        ClientController.actualizeCart(request, currentClient, shoppingCartManager);
    } else {
        shoppingCartManager.setShoppingCart((ShoppingCart) session.getAttribute("cart"));
    }

    orderLines = shoppingCartManager.getShoppingCart().getItems();
    session.setAttribute("cartManager", shoppingCartManager);

%>

<div class="cart_penal">


    <%
        if (!orderLines.isEmpty()) {
    %>

    <br><strong>Корзина</strong>

    <br><a href="/clearCart">Очистить корзину</a></br></p>

    <form name="order_form" action="/CreateOrder" method="post">

        <style>
            table {
                font-size: 13px;
                border-collapse: collapse;
                width: 80%;
            }

            th, td {
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #f2f2f2
            }

            th {
                background-color: #4CAF50;
                color: white;
            }
        </style>
        <table border="1">
            <tr>
                <td>id</td>
                <td>Название</td>
                <td>Количество</td>
                <td></td>
            </tr>
            <%
                for (OrderLine line : orderLines) {
            %>
            <tr>
                <td><%=line.getBook().getId()%>
                </td>
                <td><%=line.getBook().getName()%>
                </td>
                <td><input type="number" name="q-<%=line.getBook().getId()%>" min="1" value="<%=line.getQuantity()%>"
                           onchange=""
                           style="width:45px"></td>
                <td><a href="/removeOrderLine?id=<%=line.getBook().getId()%>">
                    <img src="../images/delete.png" alt="Удалить" name="delete"/></a>
                </td>
            </tr>
            <%
                }
                request.getSession().setAttribute("orderLines", orderLines);

//                session.setAttribute("cart", shoppingCartManager.getShoppingCart());
                //CartController.writeBooksIntoCookie(request, response, newBook.getId(), previousQuantity, shoppingCartManager);
                // как сделать чтобы при обновлении value сразу перезаписывались кукисы без дж эс?
            %>
        </table>

        <br>Способ доставки:<select name="shipping_type">
        <%
            for (ShippingType value : ShippingType.values()) {
        %>
        <option><%=value.toString()%>
        </option>
        <% } %>
    </select>


        <br>Способ оплаты:<select name="payment_type">
        <%
            for (PaymentType value : PaymentType.values()) {
        %>
        <option><%=value.toString()%>
        </option>
        <% } %>
    </select>

        <% if (request.getUserPrincipal() != null) {%>
        <p></p>

        <p><input type="submit" value="Оформить заказ"></p>
        <% } else { %>
        <br><a href="/user_pages/profile.jsp">Войдите</a>, чтобы сделать заказ</br></p>
        <% } %>


    </form>
    <%
    } else {
    %>
    <br><strong>Ваша корзина пока пуста</strong>
    <%
        }
    %>

</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css" type="text/css"/>
<div id="order_popup_ok" class="overlay">
    <div class="popup">
        <h2>Заказ</h2>
        <a class="close" href="">×</a>

        <div>
            Ваш заказ передан в обработку
        </div>
    </div>
</div>

<div id="order_popup_not_ok" class="overlay">
    <div class="popup">
        <h2>Заказ</h2>
        <a class="close" href="">×</a>

        <div>
            Заказ не оформлен. Попробуйте позже!
        </div>
    </div>
</div>

</body>
</html>
