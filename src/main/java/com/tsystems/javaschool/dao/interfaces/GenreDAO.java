package com.tsystems.javaschool.dao.interfaces;


import com.tsystems.javaschool.dao.entity.Genre;

import javax.persistence.EntityManager;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 08.02.2016
 */
public interface GenreDAO extends AbstractJpaDAO<Genre> {
    Genre findByName(String name);

    void setNullBeforeDelete(Genre entity);
}
