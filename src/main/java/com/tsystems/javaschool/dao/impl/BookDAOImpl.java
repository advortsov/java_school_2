package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 10.02.2016
 */
@Repository
public class BookDAOImpl extends AbstractJpaDAOImpl<Book> implements BookDAO {

    private final static Logger logger = Logger.getLogger(BookDAOImpl.class);

    @Autowired
    private BookManager bookManager;

    public BookDAOImpl() {
        super();
        setClazz(Book.class);
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        logger.debug("Sorting map by value...");
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        logger.debug("Map has been sorted.");
        return result;
    }

    public List<Book> findByName(String name) {
        logger.debug("Getting books by book name...");
        List<Book> books = null;
        String sql = "SELECT a FROM Book a WHERE a.name = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        books = findMany(query);
        logger.debug("Returning books.");
        return books;
    }

    public List<Book> findByGenre(Genre genre) {
        logger.debug("Finding books by genre...");
        List<Book> books = null;
        String sql = "SELECT a FROM Book a WHERE a.genre = :genre";
        Query query = em.createQuery(sql).
                setParameter("genre", genre);
        books = findMany(query);
        logger.debug("Returning books.");
        return books;
    }

    public List<Book> findByAuthor(Author author) {
        logger.debug("Finding books by author...");
        List<Book> books = null;
        String sql = "SELECT a FROM Book a WHERE a.author = :author";
        Query query = em.createQuery(sql).
                setParameter("author", author);
        books = findMany(query);
        logger.debug("Returning books.");
        return books;
    }

    public Book findByIsbn(String isbn) {
        logger.debug("Finding book by isbn...");
        Book book = null;
        String sql = "SELECT a FROM Book a WHERE a.isbn = :isbn";
        Query query = em.createQuery(sql).
                setParameter("isbn", isbn);
        book = findOne(query);
        logger.debug("Returning book.");
        return book;
    }

    public void setBookQuantity(long bookId, int orderQuantity) {
        logger.debug("Setting new book quantity...");
        Book book = this.findByID(Book.class, bookId);
        int actualQuantity = book.getQuantity();
        book.setQuantity(actualQuantity - orderQuantity);
        merge(book);
        logger.debug("New quantity has been set.");
    }

    @Override
    public Map<Book, Integer> getTopTenBooks() {
        logger.debug("Finding top ten books...");

        Map<Book, Integer> topBooks = new HashMap<>();

        String sql = "select order_line.book_id, sum(order_line.quantity) " +
                "as total from order_line GROUP BY book_id ORDER BY total DESC LIMIT 10";

        List<Object[]> resultList = em.createNativeQuery(sql).getResultList();

        for (Object[] result : resultList) {
            BigInteger bookId = (BigInteger) result[0];
            long id = bookId.longValue();

            BigDecimal totalSold = (BigDecimal) result[1];
            int summ = totalSold.intValue();

            topBooks.put(bookManager.findBookById(id), summ);
        }
        logger.debug("Returning top ten books.");

        return sortByValue(topBooks);
    }

    @Override
    public List<Book> findByPublisher(Publisher publisher) {
        logger.debug("Finding books by publisher...");
        List<Book> books = null;
        String sql = "SELECT a FROM Book a WHERE a.publisher = :publisher";
        Query query = em.createQuery(sql).
                setParameter("publisher", publisher);
        books = findMany(query);
        logger.debug("Returning books.");
        return books;
    }

    @Override
    public List<Book> getBooksBySearch(String searchString, SearchType searchOption) {
        List<Book> currentBookList = null;

        if (searchOption.equals(SearchType.AUTHOR)) {
            String sql = "SELECT b FROM Book b WHERE lower(b.author.name) LIKE :search";
            Query query = em.createQuery(sql).setParameter("search", "%" + searchString.toLowerCase() + "%");
            currentBookList = findMany(query);
        } else if (searchOption.equals(SearchType.TITLE)) {
            String sql = "SELECT b FROM Book b WHERE lower(b.name) LIKE :search";
            Query query = em.createQuery(sql).setParameter("search", "%" + searchString.toLowerCase() + "%");
            currentBookList = findMany(query);
        } else if (searchOption.equals(SearchType.ISBN)) {
            currentBookList = new ArrayList<>();
            Book book = bookManager.findBookByIsbn(searchString);
            if (book != null) {
                currentBookList.add(book);
            }
        }

        return currentBookList;
    }


}
