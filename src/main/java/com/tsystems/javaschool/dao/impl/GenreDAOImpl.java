package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.GenreDAO;
import com.tsystems.javaschool.services.interfaces.BookManager;
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
 * @since 08.02.2016
 */
@Repository
@Transactional
public class GenreDAOImpl extends AbstractJpaDAOImpl<Genre> implements GenreDAO {

    final static Logger logger = Logger.getLogger(GenreDAOImpl.class);

    @Autowired
    BookDAO bookDAO;

    @PersistenceContext
    private EntityManager em;

    public GenreDAOImpl() {
        super();
        setClazz(Genre.class);
    }

    public Genre findByName(String name) {
        logger.info("Getting genre by genre name...");
        Genre genre = null;
        String sql = "SELECT g FROM Genre g WHERE g.name = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        genre = findOne(query);
        logger.info("Returning Genre object.");
        return genre;
    }

    @Override
    public void setNullBeforeDelete(Genre entity) {
        logger.info("Setting null current genre fields in the books.");
        List<Book> allBooks = bookDAO.findByGenre(entity);
        for (Book book : allBooks) {
            book.setGenre(null);
            bookDAO.merge(book);
        }
        logger.info("All books with current genre has been proceed.");

    }


}

