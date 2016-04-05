package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.dao.interfaces.PublisherDAO;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.PublisherManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
@Service
@Transactional
public class PublisherManagerImpl implements PublisherManager {

    @Autowired
    private PublisherDAO publisherDAO;

    @Override
    public Publisher findByPublisherName(String name) {
        return publisherDAO.findByName(name);
    }

    @Override
    public List<Publisher> loadAllPublishers() {
        return publisherDAO.findAll(Publisher.class);
    }

    @Override
    public void saveNewPublisher(Publisher publisher) throws DuplicateException {
        publisherDAO.save(publisher);
    }

    @Override
    public Publisher findPublisherById(long id) {
        return publisherDAO.findByID(Publisher.class, id);
    }

    @Transactional
    @Override
    public void deletePublisher(Publisher publisher) {
        publisherDAO.setNullBeforeDelete(publisher);
        publisherDAO.delete(publisher);
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        publisherDAO.merge(publisher);
    }
}
