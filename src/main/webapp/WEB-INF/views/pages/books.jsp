<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<div class="book_list">

    <h5 style="text-align: left; margin-top:20px;">Found books: ${allBooks.size()}</h5>

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
                <br><strong>Publisher:</strong> ${book.publisher}
                <br><strong>Page count:</strong> ${book.pageCount}
                <br><strong>Year:</strong> ${book.publishYear}
                <br><strong>Author:</strong> ${book.author.name}
                <br><strong>Price:</strong> ${book.price} <strong> rub.</strong>
                <c:if test="${isAdmin}">
                    <br><a href="<%=request.getContextPath()%>/books/edit?id=${book.id}">Edit</a>
                </c:if>
                <p style="margin:10px;"><a href="<%=request.getContextPath()%>/cart/addToCart?book_id=${book.id}">Add to cart</a></p>
            </div>
        </div>
    </c:forEach>

</div>

<%@include file="../jspf/footer.jspf" %>
