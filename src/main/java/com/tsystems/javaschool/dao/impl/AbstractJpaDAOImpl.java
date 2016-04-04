package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.interfaces.AbstractJpaDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 06.03.2016
 */

@Repository
public abstract class AbstractJpaDAOImpl<T extends Serializable> implements AbstractJpaDAO<T> {

    private final static Logger logger = Logger.getLogger(AbstractJpaDAOImpl.class);

    @PersistenceContext
    protected EntityManager em;

    private Class<T> clazz;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public void save(T entity) {
        logger.debug("Saving entity...");
        em.persist(entity);
        logger.debug("Entity has been saved.");
    }

    public void merge(T entity) {
        logger.debug("Merging entity...");
        em.merge(entity);
        logger.debug("Entity has been merged.");
    }

    public void delete(T entity) {
        logger.debug("Deleting entity...");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        logger.debug("Entity has been deleted.");
    }

    public List<T> findMany(Query query) {
        logger.debug("Searching some entities...");
        List<T> t;
        t = (List<T>) query.getResultList();
        logger.debug("Returning List of entities.");
        return t;
    }

    public T findOne(Query query) {
        logger.debug("Searching one of entities...");
        T t;
        t = (T) query.getSingleResult();
        logger.debug("Returning found entity.");
        return t;
    }

    public T findByID(Class clazz, long id) {
        logger.debug("Searching one of entities by id...");
        T t = null;
        t = (T) em.find(clazz, id);
        logger.debug("Returning found entity.");
        return t;
    }

    public List findAll(Class clazz) {
        logger.debug("Searching all of entities...");
        List T = null;
        Query query = em.createQuery("from " + clazz.getName());
        T = query.getResultList();
        logger.debug("Returning List of entities.");
        return T;
    }

}