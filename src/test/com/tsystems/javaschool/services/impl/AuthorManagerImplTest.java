package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.interfaces.AuthorDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 31.03.2016
 */
public class AuthorManagerImplTest {

    private AuthorManagerImpl authorManager;

    @Mock
    private AuthorDAO authorDAO;


    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        authorManager = new AuthorManagerImpl();
        authorManager.setAuthorDAO(authorDAO);
    }

    @After
    public void tearDown() throws Exception {
        authorManager = null;
    }

    /**
     * Special utility method for creating author.
     */
    private Author getAuthor(int id, String name) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        return author;
    }


    private Book getBook(int id, String name, Author author) {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        return book;
    }

    @Test
    public void testFindByAuthorName() throws Exception {
        Author author1 = getAuthor(1, "author1");
        when(authorDAO.findByName("author1")).thenReturn(author1);
        Author resultAuthor = authorManager.findByAuthorName("author1");
        assertEquals(author1, resultAuthor);
        verify(authorDAO, times(1)).findByName("author1");
    }

    @Test
    public void testLoadAllAuthors() throws Exception {
        Author author1 = getAuthor(1, "author1");
        Author author2 = getAuthor(2, "author2");
        Author author3 = getAuthor(3, "author3");

        List<Author> expectedList = new ArrayList<>(Arrays.asList(author1, author2, author3));

        when(authorDAO.findAll(Author.class)).thenReturn(expectedList);
        List<Author> resultList = authorManager.loadAllAuthors();

        assertEquals(expectedList, resultList);
        verify(authorDAO, times(1)).findAll(Author.class);
    }

    @Test
    public void testSaveNewAuthor_Correct() throws Exception {
        Author author1 = getAuthor(1, "author1");
        authorManager.saveNewAuthor(author1);
        verify(authorDAO, times(1)).save(author1);
    }


    @Test
    public void testFindAuthorById() throws Exception {
        Author expectedAuthor = getAuthor(1, "author1");
        when(authorDAO.findByID(Author.class, 1)).thenReturn(expectedAuthor);
        Author result = authorManager.findAuthorById(1);
        assertEquals(expectedAuthor, result);
        verify(authorDAO, times(1)).findByID(Author.class, 1);
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author = getAuthor(1, "author1");
        authorManager.deleteAuthor(author);
        verify(authorDAO, times(1)).setNullBeforeDelete(author);
        verify(authorDAO, times(1)).delete(author);
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = getAuthor(1, "author1");
        doNothing().when(authorDAO).merge(author);
        authorManager.updateAuthor(author);
        verify(authorDAO, times(1)).merge(author);
    }
}