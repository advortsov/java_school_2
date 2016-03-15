package com.tsystems.javaschool.services.interfaces;


import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 08.02.2016
 */
public interface GenreManager {

    public Genre findByGenreName(String name);

    public List<Genre> loadAllGenres();

    public void saveNewGenre(Genre genre) throws DuplicateException;

    public void updateGenre(Genre genre);

    public Genre findGenreById(long id);

    public void deleteGenre(Genre genre);

    Genre findGenreByName(String name);
}
