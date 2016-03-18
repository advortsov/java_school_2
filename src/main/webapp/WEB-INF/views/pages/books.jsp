<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<div class="book_list">

    <h5 style="text-align: left; margin-top:20px;">Найдено книг: ${allBooks.size()}</h5>

    <c:forEach items="${allBooks}" var="book">
        <div class="book_info">
            <div class="book_title">
                <p>${book.name}</p>
            </div>
            <div class="book_image">
                <a href="">
                    <img src="<%=request.getContextPath()%>/ShowImage?index=${allBooks.indexOf(book)}"
                         height="250" width="190" alt="Обложка" id="cover"/></a>
            </div>
            <div class="book_details">
                <br><strong>ISBN:</strong> ${book.isbn}
                <br><strong>Издательство:</strong> ${book.publisher}
                <br><strong>Количество страниц:</strong> ${book.pageCount}
                <br><strong>Год издания:</strong> ${book.publishYear}
                <br><strong>Автор:</strong> ${book.author.name}
                <br><strong>Цена:</strong> ${book.price} <strong> руб.</strong>
                    <%--<% if (request.isUserInRole("admin")) {%>--%>
                    <%--<br><a href="../admin_pages/edit.jsp?book_id=${book.id}">Редактировать</a>--%>
                <br><a href="<%=request.getContextPath()%>/books/edit?id=${book.id}">Редактировать</a>
                    <%--<% } %>--%>
                <p style="margin:10px;"><a href="<%=request.getContextPath()%>/cart/addToCart?book_id=${book.id}">Добавить в корзину</a></p>
            </div>
        </div>
    </c:forEach>

</div>

<%@include file="../jspf/footer.jspf" %>
