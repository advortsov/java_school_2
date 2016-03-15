package com.tsystems.javaschool.dao.interfaces;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 08.02.2016
 */
public interface GenericDAO<T, ID extends Serializable> {

    void save(T entity, EntityManager em);

    void merge(T entity, EntityManager em);

    void delete(T entity, EntityManager em);

    List<T> findMany(Query query);

    T findOne(Query query);

    List findAll(Class clazz);

    T findByID(Class clazz, long id);

}