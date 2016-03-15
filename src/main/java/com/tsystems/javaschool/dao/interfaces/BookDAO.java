package com.tsystems.javaschool.dao.interfaces;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Publisher;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 10.02.2016
 */
public interface BookDAO extends AbstractJpaDAO<Book> {
    List<Book> findByName(String name); // could return many books with the same names

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor(Author author);

    Book findByIsbn(String isbn);

    void setBookQuantity(long bookId, int quantity);

    //admin features:

    Map<Book, Integer> getTopTenBooks();

    List<Book> findByPublisher(Publisher publisher);
}
