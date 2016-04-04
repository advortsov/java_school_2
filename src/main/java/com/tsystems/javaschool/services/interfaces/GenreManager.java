package com.tsystems.javaschool.services.interfaces;


import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 * Provides methods to interaction with Genre entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 08.02.2016
 */
public interface GenreManager {

    /**
     * Returns genre entity by it name
     *
     * @param name the genre name
     * @return Genre
     */
    Genre findByGenreName(String name);

    /**
     * Returns all genre's entities from db
     *
     * @return List<Genre>
     */
    List<Genre> loadAllGenres();

    /**
     * Save new genre to database
     *
     * @throws DuplicateException
     */
    void saveNewGenre(Genre genre) throws DuplicateException;

    /**
     * Updates some changed genre entity fields in db
     *
     * @param genre the genre to updating
     */
    void updateGenre(Genre genre);

    /**
     * Returns genre entity by it id
     *
     * @param id the genre id
     * @return Genre
     */
    Genre findGenreById(long id);

    /**
     * Deletes genre row from db by genre entity
     *
     * @param genre the genre to deleting
     */
    void deleteGenre(Genre genre);
}
