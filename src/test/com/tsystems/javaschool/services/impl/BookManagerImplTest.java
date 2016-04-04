package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 24.02.2016
 */
public class BookManagerImplTest {
    private List<Book> allBooks = new ArrayList<>();
    private List<Book> oneBookFindByName = new ArrayList<>();
    private List<Book> oneBookFindByAuthorName = new ArrayList<>();
    private List<Book> oneBookFoundBySearch = new ArrayList<>();
    private Book book2;
    private BookManager bookManager;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @Before
    public void setUp() throws Exception {

        genre1 = new Genre("Algs");

        Author author1 = new Author("Author1");
        author2 = new Author("Author2");
        Publisher publisher1 = new Publisher("Publisher1");

        byte[] img = {4, 3, 5};

        Book book1 = new Book("Java", 325, "ISBN-434352", 2013, img,
                "descr", author1, genre1,
                publisher1, 3, 2184);
        book1.setId(1);


        book2 = new Book("Java1", 3525, "ISlBN-434789352", 2013, img,
                "descr", author2, genre1,
                publisher1, 3, 2184);
        book2.setId(2);

        Book book3 = new Book("Java2", 3525, "ISlBN-4343j52", 2013, img,
                "descr", author1, genre1,
                publisher1, 3, 2184);
        book2.setId(3);


        allBooks.add(book1);
        allBooks.add(book2);
        allBooks.add(book3);
        oneBookFindByName.add(book2);
        oneBookFindByAuthorName.add(book2);
        oneBookFindByAuthorName.add(book3);
        oneBookFoundBySearch.add(book2);
    }

    @Test
    public void testLoadAllBooks() throws Exception {
        Mockery context = new Mockery();
        final BookDAO mockBookDAO = context.mock(BookDAO.class);
        bookManager = new BookManagerImpl(mockBookDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findAll(Book.class);
                will(returnValue(allBooks));
            }
        });

        List<Book> actual = bookManager.loadAllBooks();
        assertTrue("Book list size is not valid!", actual.size() == 3);
        context.assertIsSatisfied(); // то что ожидал от заглушки вызвалось
    }

    @Test
    public void testFindByBookName() throws Exception {
        Mockery context = new Mockery();
        final BookDAO mockBookDAO = context.mock(BookDAO.class);
        bookManager = new BookManagerImpl(mockBookDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByName("Java1");
                will(returnValue(oneBookFindByName));
            }
        });

        List<Book> actual = bookManager.findByBookName("Java1");
        assertTrue("The actual book is not equals to expected book!",
                actual.get(0)
                        .equals(book2));
        context.assertIsSatisfied();
    }

    @Test
    public void testFindByAuthorName() throws Exception {
        Mockery context = new Mockery();

        final BookDAO mockBookDAO = context.mock(BookDAO.class);
        final AuthorDAO mockAuthorDAO = context.mock(AuthorDAO.class);

        bookManager = new BookManagerImpl(mockBookDAO, mockAuthorDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockAuthorDAO).findByName("Author2");
                will(returnValue(author2));
            }
        });

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByAuthor(author2);
                will(returnValue(oneBookFindByAuthorName));
            }
        });

        List<Book> actual = bookManager.findByAuthorName("Author2");
        assertTrue("The actual book is not equals to expected book!",
                actual.get(0)
                        .equals(book2));
        context.assertIsSatisfied();
    }


    @Test
    public void testSaveNewBook() throws Exception {
//        Mockery context = new Mockery();
//        final BookDAO mockBookDAO = context.mock(BookDAO.class);
//        bookManager = new BookManagerImpl(mockBookDAO);
//        Genre genre4 = new Genre("Algs4");
//        Publisher publisher1 = new Publisher("Publisher1");
//
////        byte[] img = {4, 3, 5};
////        final Book book4 = new Book("Java4", 3525, "ISlBN-1052", 2013, img,
////                "descr", author2, genre4,
////                publisher1, 3, 2184);
//
////        allBooks.add(book2);
//
//        context.checking(new Expectations() {
//            {
//                oneOf(mockBookDAO).save(book2);
//                oneOf(mockBookDAO).findByIsbn("ISlBN-434789352");
//            }
//        });
//
//        bookManager.saveNewBook(book2);
//        Book actual = bookManager.findBookByIsbn("ISlBN-434789352");
//
//        System.out.println(book2);
//        System.out.println(actual);
//        assertTrue("The actual book is not equals to expected book!",
//                actual
//                        .equals(book2));
//        context.assertIsSatisfied();
    }

    @Test
    public void testUpdateBook() throws Exception {

    }

    @Test
    public void testFindBookById() throws Exception {
        Mockery context = new Mockery();
        final BookDAO mockBookDAO = context.mock(BookDAO.class);
        bookManager = new BookManagerImpl(mockBookDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByID(Book.class, 2L);
                will(returnValue(book2));
            }
        });

        Book actual = bookManager.findBookById(2L);
        assertTrue("The actual book is not equals to expected book!",
                actual.equals(book2));
        context.assertIsSatisfied();
    }

    @Test
    public void testDeleteBook() throws Exception {

    }

    @Test
    public void testGetBooksByGenre() throws Exception {
        Mockery context = new Mockery();

        final BookDAO mockBookDAO = context.mock(BookDAO.class);

        bookManager = new BookManagerImpl(mockBookDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByGenre(genre2);
                will(returnValue(oneBookFindByAuthorName));
            }
        });

        List<Book> actual = bookManager.getBooksByGenre(genre2);
        assertTrue("The actual book is not equals to expected book!",
                actual.get(0)
                        .equals(book2));
        context.assertIsSatisfied();
    }

    @Test
    public void testGetBooksBySearch() throws Exception {

//        Mockery context = new Mockery();
//
//        final BookDAO mockBookDAO = context.mock(BookDAO.class);
//
//        final BookManager bookManager1 = context.mock(BookManager.class);
//
//        bookManager = new BookManagerImpl(mockBookDAO);
//
//        context.checking(new Expectations() {
//            {
//                oneOf(mockBookDAO).findByName("Java1");
//                will(returnValue(oneBookFoundBySearch));
//            }
//        });
//
//        context.checking(new Expectations() {
//            {
//                oneOf(mockBookDAO).findByIsbn("ISlBN-434789352");
//                will(returnValue(oneBookFoundBySearch));
//            }
//        });
//        context.checking(new Expectations() {
//            {
//                oneOf(bookManager1).findByAuthorName("Author2");
//                will(returnValue(oneBookFoundBySearch));
//            }
//        });
//
//
//        List<Book> actual1 = bookManager.getBooksBySearch("Java1", SearchType.TITLE);
//        System.out.println(actual1);
//        assertTrue("The actual book is not equals to expected book!", actual1.get(0).equals(book2));
//
//        List<Book> actual3 = bookManager.getBooksBySearch("Author2", SearchType.AUTHOR);
//        assertTrue("The actual book is not equals to expected book!", actual3.get(0).equals(book2));
//
//        List<Book> actual2 = bookManager.getBooksBySearch("ISlBN-434789352", SearchType.ISBN);
//        assertTrue("The actual book is not equals to expected book!", actual2.get(0).equals(book2));
//
//        context.assertIsSatisfied();

//
//        book2 = new Book("Java1", 3525, "ISlBN-434789352", 2013, img,
//                "descr", author2, genre1,
//                publisher1, 3, 2184);
//        book2.setId(2);
    }

    @Test
    public void testGetBookQuantity() throws Exception {
        Mockery context = new Mockery();

        final BookDAO mockBookDAO = context.mock(BookDAO.class);

        bookManager = new BookManagerImpl(mockBookDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByID(Book.class, 2);
                will(returnValue(book2));
            }
        });

        int quantity = bookManager.getBookQuantity(2);
        assertTrue("The actual book is not equals to expected book!",
                quantity == book2.getQuantity());
        context.assertIsSatisfied();
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        Mockery context = new Mockery();

        final BookDAO mockBookDAO = context.mock(BookDAO.class);
        final AuthorDAO mockAuthorDAO = context.mock(AuthorDAO.class);

        bookManager = new BookManagerImpl(mockBookDAO, mockAuthorDAO);

        context.checking(new Expectations() {
            {
                oneOf(mockAuthorDAO).findByName("Author2");
                will(returnValue(author2));
            }
        });

        context.checking(new Expectations() {
            {
                oneOf(mockBookDAO).findByAuthor(author2);
                will(returnValue(oneBookFindByAuthorName));
            }
        });

        List<Book> actual = bookManager.findByAuthorName("Author2");
        assertTrue("The actual book is not equals to expected book!",
                actual.get(0)
                        .equals(book2));
        context.assertIsSatisfied();
    }
}