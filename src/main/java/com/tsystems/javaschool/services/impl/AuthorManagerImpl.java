package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.AuthorManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
@Service
@Transactional
public class AuthorManagerImpl implements AuthorManager {

    private final static Logger logger = Logger.getLogger(AuthorManagerImpl.class);

    @Autowired
    private AuthorDAO authorDAO;

    public AuthorManagerImpl() {
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public void setAuthorDAO(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public Author findByAuthorName(String name) {
        logger.debug("Returning author by his name.");
        return authorDAO.findByName(name);
    }

    @Override
    public List<Author> loadAllAuthors() {
        logger.debug("Returning all authors.");
        return authorDAO.findAll(Author.class);
    }

    @Override
    public void saveNewAuthor(Author author) throws DuplicateException {
        logger.debug("Trying to save new author...");
        authorDAO.save(author);
    }

    @Override
    public Author findAuthorById(long id) {
        logger.debug("Returning author by his id.");
        return authorDAO.findByID(Author.class, id);
    }

    @Override
    @Transactional
    public void deleteAuthor(Author author) {
        logger.debug("Trying to delete author...");
        logger.debug("Setting this author to null of all books before deleting...");
        authorDAO.setNullBeforeDelete(author);
        logger.debug("Deleting author...");
        authorDAO.delete(author);
        logger.debug("Author has been successfully deleted!");
    }

    @Override
    public void updateAuthor(Author author) {
        logger.debug("Trying to update author...");
        authorDAO.merge(author);
    }
}
