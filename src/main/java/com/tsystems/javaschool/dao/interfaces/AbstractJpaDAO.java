package com.tsystems.javaschool.dao.interfaces;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 06.03.2016
 */
public interface AbstractJpaDAO<T> {

    void setClazz(final Class<T> clazzToSet);

    void save(T entity);

    void merge(T entity);

    void delete(T entity);

    List<T> findMany(Query query);

    T findOne(Query query);

    T findByID(Class clazz, long id);

    List findAll(Class clazz);
}
