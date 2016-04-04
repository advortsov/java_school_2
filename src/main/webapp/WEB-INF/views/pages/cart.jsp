<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<link href="<c:url value="/resources/css/style_main.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/cart.js" />"></script>

<body onload="total(${fn:length(shoppingCart.items)})">
<div class="cart_penal">

    <c:choose>
        <c:when test="${not empty shoppingCart.items}">
            <br><strong>Shopping cart</strong>
            <br><a href="/cart/clearCart">Clear cart</a></br></p>

            <form:form name="order_form" id="order_form"
                       modelAttribute="createdOrder" action="/order/create-order" method="post">
                <style>
                    table {
                        font-size: 14px;
                        border-collapse: collapse;
                        width: 80%;
                        border: 0;

                    }

                    th, td {
                        text-align: left;
                        padding: 8px;
                        border-width: 0 0px 0px 0;

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
                        <td><strong>id</strong></td>
                        <td><strong>Name</strong></td>
                        <td><strong>Price</strong></td>
                        <td><strong>Count</strong></td>
                        <td><strong>Total</strong></td>
                        <td></td>
                    </tr>
                    <c:forEach items="${shoppingCart.items}" var="line" varStatus="count">
                        <input type="hidden" name="list_size" id="list_size" value="${fn:length(shoppingCart.items)}"></p>

                        <tr>
                            <div class="line">
                                <td>
                                    <div id='book_id${count.index}' name="book_id">${line.book.id}</div>
                                </td>
                                <td>${line.book.name}</td>
                                <td>
                                    <div id='book_price${count.index}' name="book_price">${line.book.price}</div>
                                </td>

                                <td><input type="number" name="q-${line.book.id}" min="1" value="${line.quantity}"
                                           id="books_quantity${count.index}"
                                           name="books_quantity"
                                           onchange="total(${fn:length(shoppingCart.items)})"
                                           style="width:45px"></td>

                                <td>
                                    <div id='total_summ_of_line${count.index}'
                                         name="total_summ_of_line">${line.book.price} rub.
                                    </div>
                                </td>

                                <td><a href="/cart/removeOrderLine?id=${line.book.id}">
                                    <img src="../../../resources/images/delete.png" alt="Delete" name="delete"/></a>
                                </td>
                            </div>
                        </tr>
                    </c:forEach>

                </table>

                <br>Total order summary:
                <div style="font-size: x-large" id='total_summ_of_order' name="total_summ_of_order"></div>
                <p>&nbsp;</p>

                <td style="color: red;"><form:errors path="orderLines"/></td>


                <br>Shipping type:<select name="shipping_type">
                <c:forEach items="${shippingTypeList}" var="shippingType">
                    <option>${shippingType}</option>
                </c:forEach>
                </select>

                <p></p>
                <br>Payment type:<select name="payment_type">
                <c:forEach items="${paymentTypeList}" var="paymentType">
                    <option>${paymentType}</option>
                </c:forEach>
                </select>

                <p>&nbsp;</p>

                <span style="color: red; float: right" id="books_quantity_error"></span>

                <c:choose>
                    <c:when test="${loggedIn}">
                        <p><input type="submit" value="Create order"></p>
                    </c:when>
                    <c:otherwise>
                        <br><a href="/logout">Log in</a>, to make an order</br></p>
                    </c:otherwise>
                </c:choose>

            </form:form>

        </c:when>
        <c:otherwise>
            <br><strong>Your shopping cart is empty</strong>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty success}">
        <% response.sendRedirect("/cart#order_popup_ok"); %>
    </c:if>


</div>

<div id="order_popup_ok" class="overlay">
    <div class="popup">
        <h2>Order</h2>
        <a class="close" href="">×</a>

        <div>
            Your order has been created!
        </div>
    </div>
</div>

<%@include file="../jspf/footer.jspf" %>

