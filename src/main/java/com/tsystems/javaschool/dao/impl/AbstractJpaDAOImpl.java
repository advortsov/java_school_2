package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.interfaces.AbstractJpaDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public abstract class AbstractJpaDAOImpl<T extends Serializable> implements AbstractJpaDAO<T> {
    final static Logger logger = Logger.getLogger(AbstractJpaDAOImpl.class);

    private Class<T> clazz;

    @PersistenceContext
    private EntityManager em;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public void save(T entity) {
        logger.info("Saving entity...");
        em.persist(entity);
        logger.info("Entity has been saved.");
    }

    public void merge(T entity) {
        logger.info("Merging entity...");
        em.merge(entity);
        logger.info("Entity has been merged.");
    }

    public void delete(T entity) {
        logger.info("Deleting entity...");
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        logger.info("Entity has been deleted.");
    }

    public List<T> findMany(Query query) {
        logger.info("Searching some entities...");
        List<T> t;
        t = (List<T>) query.getResultList();
        logger.info("Returning List of entities.");
        return t;
    }

    public T findOne(Query query) {
        logger.info("Searching one of entities...");
        T t;
        t = (T) query.getSingleResult();
        logger.info("Returning found entity.");
        return t;
    }

    public T findByID(Class clazz, long id) {
        logger.info("Searching one of entities by id...");
        T t = null;
        t = (T) em.find(clazz, id);
        logger.info("Returning found entity.");
        return t;
    }

    public List findAll(Class clazz) {
        logger.info("Searching all of entities...");
        List T = null;
        Query query = em.createQuery("from " + clazz.getName());
        T = query.getResultList();
        logger.info("Returning List of entities.");
        return T;
    }

}