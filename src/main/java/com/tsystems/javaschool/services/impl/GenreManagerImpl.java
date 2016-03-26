package com.tsystems.javaschool.services.impl;


import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.interfaces.GenreDAO;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    GenreDAO genreDAO;

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
//        } catch (PersistenceException ex) {
//            ex.printStackTrace();
//            JpaUtil.rollbackTransaction(em);
//            throw new DuplicateException();
//        }
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

    @Override
    public Genre findGenreByName(String name) {
        return genreDAO.findByName(name);
    }

}