package com.tsystems.javaschool.dao.impl;


import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
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
 * @since 09.02.2016
 */
@Repository
@Transactional
public class AuthorDAOImpl extends AbstractJpaDAOImpl<Author> implements AuthorDAO {

    private final static Logger logger = Logger.getLogger(AuthorDAOImpl.class);

    @Autowired
    private BookDAO bookDAO;

    @PersistenceContext
    private EntityManager em;

    public AuthorDAOImpl() {
        super();
        setClazz(Author.class);
    }

    @Override
    public void delete(Author entity) { // to unite different methods in the one transaction
        logger.debug("Staring author deleting by author entity...");
        setNullBeforeDelete(entity);
        super.delete(entity);
    }

    public Author findByName(String name) {
        logger.debug("Getting author by author name...");
        Author author = null;
        String sql = "SELECT a FROM Author a WHERE a.name = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        author = findOne(query);
        return author;
    }

    public void setNullBeforeDelete(Author author) {
        logger.debug("Setting null current genre fields in the books.");
        List<Book> allBooks = bookDAO.findByAuthor(author);
        for (Book book : allBooks) {
            book.setAuthor(null);
            bookDAO.merge(book);
        }
        logger.debug("All books with current author has been set to null.");
    }
}

