package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.services.exception.DuplicateException;

import java.util.List;

/**
 * Provides methods to interaction with Publisher entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
public interface PublisherManager {

    /**
     * Returns publisher entity by it name
     *
     * @param name the publisher name
     * @return Publisher
     */
    Publisher findByPublisherName(String name);

    /**
     * Returns all publisher's entities from db
     *
     * @return List<Publisher>
     */
    List<Publisher> loadAllPublishers();

    /**
     * Save new publisher to database
     *
     * @throws DuplicateException
     */
    void saveNewPublisher(Publisher publisher) throws DuplicateException;

    /**
     * Returns publisher entity by it id
     *
     * @param id the publisher id
     * @return Publisher
     */
    Publisher findPublisherById(long id);

    /**
     * Deletes publisher row from db by publisher entity
     *
     * @param publisher the publisher to deleting
     */
    void deletePublisher(Publisher publisher);

    /**
     * Updates some changed publisher entity fields in db
     *
     * @param publisher the publisher to updating
     */
    void updatePublisher(Publisher publisher);
}