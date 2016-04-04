package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 * Provides methods to interaction with Book entity
 *
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

    /**
     * Returns all found books from db
     *
     * @return List<Book>
     */
    List<Book> loadAllBooks();

    /**
     * Save new book to database
     *
     * @return List<Book>
     * @throws DuplicateException
     */
    void saveNewBook(Book book) throws DuplicateException;

    /**
     * Returns book entity by it id
     *
     * @param id the book id
     * @return Book
     */
    Book findBookById(long id);

    /**
     * Returns books entities found by it genre
     *
     * @param genre the Genre entity
     * @return List<Book>
     */
    List<Book> getBooksByGenre(Genre genre);

    /**
     * Returns books entities found by it author name
     *
     * @param name the name of book author
     * @return List<Book>
     */
    List<Book> findByAuthorName(String name);

    /**
     * Returns books entities found by search
     *
     * @param searchStr what we want to found
     * @param type      search type (title, author, isbn)
     * @return List<Book>
     */
    List<Book> getBooksBySearch(String searchStr, SearchType type);

    /**
     * Updates some changed book entity fields in db
     *
     * @param book the book to updating
     */
    void updateBook(Book book) throws DuplicateException;

    /**
     * Returns books quantity in the stock
     *
     * @param id the book id
     * @return int quantity
     */
    int getBookQuantity(long id);

    /**
     * Returns books entities found by it author
     *
     * @param author the Author entity
     * @return List<Book>
     */
    List<Book> getBooksByAuthor(Author author);

    /**
     * Returns book entity by it isbn
     *
     * @param value the book isbn
     * @return Book
     */
    Book findBookByIsbn(String value);
}
