<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<link href="<c:url value="/resources/css/style_main.css" />" rel="stylesheet">

<body onload="total(${fn:length(shoppingCart.items)})">
<div class="cart_penal">

    <c:choose>
        <c:when test="${not empty shoppingCart.items}">
            <br><strong>Корзина</strong>
            <br><a href="/cart/clearCart">Очистить корзину</a></br></p>

            <form:form name="order_form" id="order_form"
                       modelAttribute="createdOrder" action="/order/create-order" method="post">
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
                                         name="total_summ_of_line">${line.book.price}</div>
                                </td>

                                <td><a href="/cart/removeOrderLine?id=${line.book.id}">
                                    <img src="../../../resources/images/delete.png" alt="Удалить" name="delete"/></a>
                                </td>
                            </div>
                        </tr>
                    </c:forEach>

                </table>

                <br>Общая сумма заказа:
                <div id='total_summ_of_order' name="total_summ_of_order"></div>


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

                <span style="color: red; float: right" id="books_quantity_error"></span>


                <c:choose>
                    <c:when test="${loggedIn}">
                        <p><input type="submit" value="Оформить заказ"></p>
                    </c:when>
                    <c:otherwise>
                        <br><a href="/profile">Войдите</a>, чтобы сделать заказ</br></p>
                    </c:otherwise>
                </c:choose>

            </form:form>


            <script>
                function total(linesSize) {
                    // total lines and order summ counting:
                    var orderSumm = 0;
                    for (var i = 0; i < linesSize; i++) {
                        var item = document.getElementById("total_summ_of_line" + i);
                        var res = parseInt(document.getElementById("book_price" + i).innerHTML) *
                                parseInt(document.getElementById("books_quantity" + i).value);
                        item.innerHTML = res.toString();
                        orderSumm += parseInt(item.innerHTML);
                    }
                    document.getElementById("total_summ_of_order").innerHTML = orderSumm.toString();
                }


                function booksQuantityValidate() {
                    var size = parseInt(document.getElementById("list_size").value);
                    var isOk = true;

                    for (var i = 0; i < size; i++) {
                        var bookId = parseInt(document.getElementById("book_id" + i).innerHTML);
                        var bookQuantity = parseInt(document.getElementById("books_quantity" + i).value);
                        var jqXHR = $.ajax({
                            url: 'order/ajaxBooksQuantityValidation',
                            data: ({bookId: bookId, bookQuantity: bookQuantity}),
                            async: false,
                            success: function (data) {
                                if (data.length > 1) {
                                    isOk = false;
                                    $('#books_quantity_error').html(data);
                                }
                            }
                        });
                    }
                    return isOk;
                }

                $(document).ready(function () {
                    $("#order_form").submit(function () {
                        var isValidated = booksQuantityValidate();
                        return isValidated;
                    });
                });
            </script>


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

