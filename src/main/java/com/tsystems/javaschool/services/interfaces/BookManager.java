package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 10.02.2016
 */
public interface BookManager {

    /**
     * Returns books entity by the name
     *
     * @param name the title of book (or books)
     * @return List<Book>
     */
    List<Book> findByBookName(String name);

    List<Book> loadAllBooks();

    void saveNewBook(Book book) throws DuplicateException;

    Book findBookById(long id);

    void deleteBook(Book book);

    List<Book> getBooksByGenre(Genre genre);

    List<Book> findByAuthorName(String name);

    List<Book> getBooksBySearch(String searchStr, SearchType type);

    void updateBook(Book book) throws DuplicateException;

    int getBookQuantity(long id);

    List<Book> getBooksByAuthor(Author author);

    Book findBookByIsbn(String value);
}
