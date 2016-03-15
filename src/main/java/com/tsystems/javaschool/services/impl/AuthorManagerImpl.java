package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.AuthorManager;
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
public class AuthorManagerImpl implements AuthorManager {

    @Autowired
    AuthorDAO authorDAO;

    @Override
    public Author findByAuthorName(String name) {
        return authorDAO.findByName(name);
    }

    @Override
    public List<Author> loadAllAuthors() {
        return authorDAO.findAll(Author.class);
    }

    @Override
    public void saveNewAuthor(Author author) throws DuplicateException {
        authorDAO.save(author);
    }

    @Override
    public Author findAuthorById(long id) {
        return authorDAO.findByID(Author.class, id);
    }

    @Override
    @Transactional
    public void deleteAuthor(Author author) {
        authorDAO.setNullBeforeDelete(author);
        authorDAO.delete(author);
    }

    @Override
    public void updateAuthor(Author author) {
        authorDAO.merge(author);
    }
}
