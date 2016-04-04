package com.tsystems.javaschool.services.impl;


import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.interfaces.GenreDAO;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 08.02.2016
 */

@Service
@Transactional
public class GenreManagerImpl implements GenreManager {

    @Autowired
    private GenreDAO genreDAO;

    @Override
    public Genre findByGenreName(String name) {
        return genreDAO.findByName(name);
    }

    @Override
    public List<Genre> loadAllGenres() {
        return genreDAO.findAll(Genre.class);
    }

    @Override
    public void saveNewGenre(Genre genre) throws DuplicateException {
        genreDAO.save(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreDAO.merge(genre);
    }

    @Override
    public Genre findGenreById(long id) {
        return genreDAO.findByID(Genre.class, id);
    }

    @Override
    @Transactional
    public void deleteGenre(Genre genre) {
        genreDAO.setNullBeforeDelete(genre);
        genreDAO.delete(genre);
    }

}