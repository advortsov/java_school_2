<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<link href="<c:url value="/resources/css/style_main.css" />" rel="stylesheet">

<div class="cart_penal">

    <c:choose>
        <c:when test="${not empty shoppingCart.items}">
            <br><strong>Корзина</strong>
            <br><a href="/cart/clearCart">Очистить корзину</a></br></p>

            <form:form name="order_form"
                       modelAttribute="createdOrder" action="/order/create-order" method="post">
                <%--<form name="order_form" action="/create_order" method="post">--%>

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
                        <td>Цена</td>
                        <td>Количество</td>
                        <td>Стоимость</td>
                        <td></td>
                    </tr>
                    <c:forEach items="${shoppingCart.items}" var="line">
                        <tr>
                            <div class="line">
                                <td>${line.book.id}</td>
                                <td>${line.book.name}</td>
                                <td><input type="number" value="${line.book.price}" name="book_price" id="book_price"
                                           readonly></td>

                                <td><input type="number" name="q-${line.book.id}" min="1" value="${line.quantity}"
                                           id="books_quantity"
                                           name="books_quantity"
                                           style="width:45px"></td>

                                <td><input type="number" name="total_summ_of_line"
                                           id="total_summ_of_line"
                                           contenteditable="false">
                                </td>

                                <td><a href="/cart/removeOrderLine?id=${line.book.id}">
                                    <img src="../../../resources/images/delete.png" alt="Удалить" name="delete"/></a>
                                </td>
                            </div>
                        </tr>
                    </c:forEach>

                </table>

                <br>Общая сумма заказа:<input type="number" name="total_summ_of_order"
                id="total_summ_of_order"
                style="width:100px">


                <br>Способ доставки:<select name="shipping_type">
                <c:forEach items="${shippingTypeList}" var="shippingType">
                    <option>${shippingType}</option>
                </c:forEach>
                </select>


                <br>Способ оплаты:<select name="payment_type">
                <c:forEach items="${paymentTypeList}" var="paymentType">
                    <option>${paymentType}</option>
                </c:forEach>
                </select>

                <div class="field">
                    <td class="error"><form:errors path="book_count"/></td>
                </div>

                <c:choose>
                    <c:when test="${loggedIn}">
                        <p><input type="submit" value="Оформить заказ"></p>
                    </c:when>
                    <c:otherwise>
                        <br><a href="/profile">Войдите</a>, чтобы сделать заказ</br></p>
                    </c:otherwise>
                </c:choose>

                <%--</form>--%>
            </form:form>
        </c:when>
        <c:otherwise>
            <br><strong>Ваша корзина пока пуста</strong>
        </c:otherwise>
    </c:choose>

</div>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/popup.css" type="text/css"/>
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

        <div>Заказ не оформлен. Попробуйте позже!</div>
    </div>
</div>

<%@include file="../jspf/footer.jspf" %>

