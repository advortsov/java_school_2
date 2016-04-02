<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>


<div class="edit_penal">

    <br><strong>Editing book</strong>

    <p>&nbsp;</p>

    <c:url var="saveUrl" value="/books/edit?id=${book.id}" />
    <form name="book_edit_form" enctype="multipart/form-data" accept-charset="utf-8"
          action="${saveUrl}" method="post">
        <div class="edit_book_info">
            <div>

                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css"/>

                <div class="field">
                    <label for="cover">Cover:</label><input type="file" name="cover" id="cover" multiple
                                                              accept="image/jpeg">
                </div>
                <div class="field">
                    <label for="book_name">Name:</label><input name="book_name" type="text" id="book_name"
                                                                   pattern=".{5,255}"
                                                                   required
                                                                   value="${book.name}"
                                                                   title="Название может содержать от 5 до 255 символов">
                </div>
                <div class="field">
                    <label for="book_isbn">ISBN:</label><input name="book_isbn" type="text" id="book_isbn"
                                                               pattern=".{5,35}"
                                                               required
                                                               value="${book.isbn}"
                                                               title="ISBN может содержать от 5 до 35 символов">
                </div>

                <div class="field">
                    <label for="book_genre">Genre:</label><select name="book_genre" id="book_genre">
                    <c:forEach items="${allGenresList}" var="genre">
                        <option>${genre.name}</option>
                    </c:forEach>
                </select>
                </div>

                <div class="field">
                    <label for="book_publisher">Publisher:</label><select name="book_publisher" id="book_publisher">
                    <c:forEach items="${allPublishersList}" var="publisher">
                        <option>${publisher.name}</option>
                    </c:forEach>
                </select>
                </div>

                <div class="field">
                    <label for="book_author">Author:</label><select name="book_author" id="book_author">
                    <c:forEach items="${allAuthorsList}" var="author">
                        <option>${author.name}</option>
                    </c:forEach>
                </select>
                </div>

                <div class="field">
                    <label for="book_pages">Pages:</label><input name="book_pages" type="number"
                                                                              id="book_pages" pattern="[0-9]{2,4}"
                                                                              required
                                                                              value="${book.pageCount}"
                                                                              title="Количество страниц может быть от 10 до 9999">
                </div>
                <div class="field">
                    <label for="book_year">Year:</label><input name="book_year" type="number" id="book_year"
                                                                      pattern="[0-9]{4,4}"
                                                                      required
                                                                      value="${book.publishYear}"
                                                                      title="Год должен состоять из 4 цифр">
                </div>
                <div class="field">
                    <label for="book_count">Quantity:</label><input name="book_count" type="number" id="book_count"
                                                                      pattern="[0-9]{1,3}"
                                                                      required
                                                                      value="${book.quantity}"
                                                                      title="Количество экземпляров одной книги на складе может быть до 999">
                </div>
                <div class="field">
                    <label for="book_price">Price, rub:</label><input name="book_price" type="number" id="book_price"
                                                                     pattern="[0-9]{1,5}"
                                                                     value="${book.price}"
                                                                     required title="Цена может быть до 99 999 руб.">
                </div>

                <input type="hidden" name="action" value="edit"></p>
                <p><input type="submit" value="Save"></p>
            </div>
        </div>

    </form>

</div>

<%@include file="../jspf/footer.jspf" %>
