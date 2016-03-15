package com.tsystems.javaschool.dao.interfaces;

import com.tsystems.javaschool.dao.entity.Publisher;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
public interface PublisherDAO extends AbstractJpaDAO<Publisher> {
    Publisher findByName(String name);

    void setNullBeforeDelete(Publisher publisher);
}
