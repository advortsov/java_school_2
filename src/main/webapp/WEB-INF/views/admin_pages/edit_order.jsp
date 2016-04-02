<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>

<div class="edit_penal">
    <br><strong>Order properties</strong>

    <form name="order_set_status_form" action="/admin/edit_order" method="post">

        <div class="edit_book_info">
            <div>
                <div class="field">
                    <label for="id">Order ID:</label> ${id}
                    <input type="text" value="${id}"
                           name="id" id="id" hidden>
                </div>

                <div class="field">
                    <label for="order_status">Order status:</label>
                    <select name="order_status" id="order_status">
                        <c:forEach items="${orderStatusList}" var="status">
                            <option value="${status}" ${status == editOrder.orderStatus ? 'selected' : ''}>${status}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="field">
                    <label for="payment_status">Payment status:</label>
                    <select name="payment_status" id="payment_status">
                        <c:forEach items="${paymentStatusList}" var="pStatus">
                            <option value="${pStatus}" ${pStatus == editOrder.paymentStatus ? 'selected' : ''}>${pStatus}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="field">
                    <label for="shipping_type">Shipping type:</label>
                    <select name="shipping_type" id="shipping_type">
                        <c:forEach items="${shippingTypeList}" var="type">
                            <option value="${type}" ${type == editOrder.shippingType ? 'selected' : ''}>${type}</option>
                        </c:forEach>
                    </select>
                </div>
                <p><input type="submit" value="Save"></p>
            </div>
        </div>


    </form>


</div>

<%@include file="../jspf/footer.jspf" %>