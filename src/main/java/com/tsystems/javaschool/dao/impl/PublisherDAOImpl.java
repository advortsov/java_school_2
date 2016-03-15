package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.PublisherDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
@Repository
@Transactional
public class PublisherDAOImpl extends AbstractJpaDAOImpl<Publisher> implements PublisherDAO {

    final static Logger logger = Logger.getLogger(GenreDAOImpl.class);

    @Autowired
    BookDAO bookDAO;
    @PersistenceContext
    private EntityManager em;

    public PublisherDAOImpl() {
        super();
        setClazz(Publisher.class);
    }

    public Publisher findByName(String name) {
        logger.info("Getting publisher by genre name...");
        Publisher publisher = null;
        String sql = "SELECT a FROM Publisher a WHERE a.name = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        publisher = findOne(query);
        logger.info("Returning Publisher object.");
        return publisher;
    }

    @Override
    public void setNullBeforeDelete(Publisher publisher) {
        logger.info("Setting null current genre fields in the books.");
        List<Book> allBooks = bookDAO.findByPublisher(publisher);
        for (Book book : allBooks) {
            book.setPublisher(null);
            bookDAO.merge(book);
        }
        logger.info("All books with current publisher has been proceed.");
    }
}
