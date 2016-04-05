package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.services.enums.SearchType;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.AuthorManager;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import com.tsystems.javaschool.services.interfaces.PublisherManager;
import com.tsystems.javaschool.view.controllers.validators.BookValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 20.02.2016
 */

@Controller
@RequestMapping("/books")
@SessionAttributes("allBooks")
public class BookController {

    private static Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    private PublisherManager publisherManager;

    @Autowired
    private AuthorManager authorManager;

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private BookManager bookManager;

    @Autowired
    private BookValidator bookValidator;

    @ModelAttribute("allBooks")
    public List<Book> allBooks() {
        List<Book> resultList = bookManager.loadAllBooks();
        return resultList;
    }

    @ModelAttribute(value = "allGenresList")
    public List<Genre> createAllGenresList() {
        return genreManager.loadAllGenres();
    }

    @ModelAttribute(value = "allAuthorsList")
    public List<Author> createAllAuthorsList() {
        return authorManager.loadAllAuthors();
    }

    @ModelAttribute(value = "allPublishersList")
    public List<Publisher> createAllPublishersList() {
        return publisherManager.loadAllPublishers();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String fillUpGenres(@ModelAttribute ArrayList<Genre> allGenres) {
        return "pages/books.jsp";
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET) // /books/genre?name=Maths
    public String getBooksByGenre(@RequestParam(value = "name", required = true) String name, Model model) {
        logger.debug("Getting books by genre...");
        List<Book> currentBookList;
        if (name.equals("all")) {
            currentBookList = bookManager.loadAllBooks();
        } else {
            currentBookList = bookManager.getBooksByGenre(genreManager.findByGenreName(name));
        }
        model.addAttribute("allBooks", currentBookList);
        model.addAttribute("currentGenre", name);
        logger.debug("Returning page with those books.");
        return "pages/books.jsp";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getBooksBySearch(@RequestParam(value = "search_string", required = true) String searchString,
                                   @RequestParam(value = "search_option", required = true) SearchType searchOption,
                                   Model model) {
        logger.debug("Getting books by search...");
        List<Book> currentBookList = bookManager.getBooksBySearch(searchString, searchOption);
        model.addAttribute("allBooks", currentBookList);
        logger.debug("Returning page with those books.");
        return "pages/books.jsp";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {
        logger.debug("Received request to show the book add page");
        model.addAttribute("bookAttribute", new Book());
        return "admin_pages/admin.jsp";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addBook(
            @ModelAttribute("uploadedBook") Book uploadedBook,
            BindingResult result,
            @RequestParam("name") String name,
            @RequestParam("isbn") String isbn,
            @RequestParam("genre") String genre,
            @RequestParam("publisher") String publisher,
            @RequestParam("author") String author,
            @RequestParam("pages") int pages,
            @RequestParam("publishYear") int year,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") int price,
            @RequestParam(value = "cover", required = false)
            CommonsMultipartFile locationMapFileData,
            ModelAndView mav,
            HttpSession session) throws DuplicateException {

        logger.debug("Starting to add new book...");

        Book book = new Book();

        book.setName(name);
        book.setIsbn(isbn);
        book.setGenre(genreManager.findByGenreName(genre));
        book.setPublisher(publisherManager.findByPublisherName(publisher));
        book.setAuthor(authorManager.findByAuthorName(author));
        book.setPageCount(pages);
        book.setPublishYear(year);
        book.setQuantity(quantity);
        book.setPrice(price);

        bookValidator.validate(book, result);

        if (result.hasErrors()) {
            session.setAttribute("book", book);
            mav.setViewName("admin_pages/admin.jsp");
            return mav;
        }

        if (locationMapFileData.getBytes().length != 0) {
            book.setImage(locationMapFileData.getBytes());
        }

        bookManager.saveNewBook(book);

        session.setAttribute("book", null);
        mav.addObject("book_added", "The book has been added");
        mav.setViewName("admin_pages/admin.jsp");

        logger.debug("Return a book added status page.");
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEdit(@RequestParam(value = "id", required = true) Integer id,
                          Model model) {
        logger.debug("Received request to show book edit page");
        model.addAttribute("book", bookManager.findBookById(id));
        return "admin_pages/edit.jsp";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEditBook(@ModelAttribute("book") Book book,
                               @RequestParam("book_name") String name,
                               @RequestParam("book_isbn") String isbn,
                               @RequestParam("book_genre") String genre,
                               @RequestParam("book_publisher") String publisher,
                               @RequestParam("book_author") String author,
                               @RequestParam("book_pages") int pages,
                               @RequestParam("book_year") int year,
                               @RequestParam("book_count") int quantity,
                               @RequestParam("book_price") int price,
                               @RequestParam(value = "cover", required = false) CommonsMultipartFile coverFileData) throws DuplicateException {

        logger.debug("Starting to edit book...");

        book.setName(name);
        book.setIsbn(isbn);
        book.setGenre(genreManager.findByGenreName(genre));
        book.setPublisher(publisherManager.findByPublisherName(publisher));
        book.setAuthor(authorManager.findByAuthorName(author));
        book.setPageCount(pages);
        book.setPublishYear(year);
        book.setQuantity(quantity);
        book.setPrice(price);

        if (coverFileData.getBytes().length != 0) {
            book.setImage(coverFileData.getBytes());
        }

        bookManager.updateBook(book);
        logger.debug("Book has been updated.");

        return "redirect:/books/genre?name=all";
    }

}
