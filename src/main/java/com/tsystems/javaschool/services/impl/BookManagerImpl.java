package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 10.02.2016
 */
@Service
@Transactional
public class BookManagerImpl implements BookManager {

    final static Logger logger = Logger.getLogger(BookManagerImpl.class);//    PropertyConfigurator.configure("log4j.properties");

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private AuthorDAO authorDAO;

    public BookManagerImpl() {
    }

    public BookManagerImpl(BookDAO mockBookDAO, AuthorDAO mockAuthorDAO) {
        this.bookDAO = mockBookDAO;
        this.authorDAO = mockAuthorDAO;
    }

    public BookManagerImpl(BookDAO mockBookDAO) {
        this.bookDAO = mockBookDAO;
    }

    @Override
    public List<Book> findByBookName(String name) {
        logger.info("Try to get books by book name...");
        return bookDAO.findByName(name);
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        logger.info("Try to get books by author name...");
        Author author = authorDAO.findByName(name);
        return bookDAO.findByAuthor(author);
    }

    @Override
    public List<Book> loadAllBooks() {
        logger.info("Loading all books list...");
        return bookDAO.findAll(Book.class);
    }

    @Override
    public void saveNewBook(Book book) throws DuplicateException {
        logger.info("Try to save new book...");
        bookDAO.save(book);
        logger.info("New book has been saved.");
    }

    @Override
    public void updateBook(Book book) throws DuplicateException {
        logger.info("Try to update book...");
        bookDAO.merge(book);
        logger.info("New book has been updated.");
    }

    @Override
    public Book findBookById(long id) {
        logger.info("Try to get one book by id...");
        return bookDAO.findByID(Book.class, id);
    }

    @Override
    public void deleteBook(Book book) {
        logger.info("Try to delete book...");
        bookDAO.delete(book);
        logger.info("New book has been deleted.");
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        logger.info("Try to get books by genre...");
        return bookDAO.findByGenre(genre);
    }

    @Override
    public List<Book> getBooksBySearch(String searchStr, SearchType type) {
        logger.info("Try to get books by search...");

        try {

            if (type == SearchType.AUTHOR) {
                logger.info("Searching by author...");
                return findByAuthorName(searchStr);
            } else if (type == SearchType.TITLE) {
                logger.info("Searching by title...");
                return bookDAO.findByName(searchStr);
            }
        } catch (NoResultException ex) {
            logger.info("Nothing found.");
            //ignore
        }
        return new ArrayList<>();
    }

    @Override
    public int getBookQuantity(long id) {
        logger.info("Try to get books quantity in the stock by book id...");
        return findBookById(id).getQuantity();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        logger.info("Try to get books by author...");
        return bookDAO.findByAuthor(author);
    }

    @Override
    public Book findBookByIsbn(String value) {
        logger.info("Try to get one book by id...");
        Book book = null;
        try {
            book = bookDAO.findByIsbn(value);
        } catch (NoResultException ex){

        }
        return book;
    }
}
