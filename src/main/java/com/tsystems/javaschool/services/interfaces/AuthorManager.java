package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 *
 * Provides method to interaction with Author entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
public interface AuthorManager {

    /**
     * Returns author entity by it name
     *
     * @param name the author name
     * @return Author
     */
    Author findByAuthorName(String name);

    /**
     * Returns all author's entities
     *
     * @return List<Author>
     */
    List<Author> loadAllAuthors();


    /**
     * Saved new author to database
     *
     * @throws DuplicateException
     * @return List<Author>
     */
    void saveNewAuthor(Author author) throws DuplicateException;

    /**
     * Returns author entity by it id
     *
     * @param id the author id
     * @return Author
     */
    Author findAuthorById(long id);

    /**
     * Deletes author row from db by author entity
     *
     * @param author the author to deleting
     */
    void deleteAuthor(Author author);

    /**
     * Updates some changed author entity fields in db
     *
     * @param author the author to updating
     */
    void updateAuthor(Author author);
}
