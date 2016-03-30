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
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 20.02.2016
 */

@Controller
@RequestMapping("/books")
@SessionAttributes("allBooks")
public class BookListController {
    private static Logger logger = Logger.getLogger(BookListController.class);

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


//    @RequestMapping(value = "/get-json-user", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public User getJsonUser(@RequestParam("name") String name) {
//        User user = new User();
//        user.setUserName(name);
//        return user;
//    }

    @ModelAttribute("allBooks")
    public List<Book> allBooks() {

        List<Book> resultList = bookManager.loadAllBooks();
        System.out.println("book allBook");
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

    /**
     * Method will be called in initial page load at GET /books
     */
    @RequestMapping(method = RequestMethod.GET)
    public String fillUpGenres(@ModelAttribute ArrayList<Genre> allGenres) {
        return "pages/books.jsp";
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET) // /books/genre?name=Maths
    public String getBooksByGenre(@RequestParam(value = "name", required = true) String name, Model model) {
        List<Book> currentBookList;
        if (name.equals("all")) {
            currentBookList = bookManager.loadAllBooks();
        } else {
            currentBookList = bookManager.getBooksByGenre(genreManager.findByGenreName(name));
        }
        model.addAttribute("allBooks", currentBookList);
        model.addAttribute("currentGenre", name);
        return "pages/books.jsp";
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    // /books/search?search_string=gkgkg&search_option=TITLE
    public String getBooksBySearch(@RequestParam(value = "search_string", required = true) String searchString,
                                   @RequestParam(value = "search_option", required = true) SearchType searchOption,
                                   Model model) {
        List<Book> currentBookList = null;
        if (searchOption.equals(SearchType.AUTHOR)) {
            currentBookList = bookManager.getBooksByAuthor(authorManager.findByAuthorName(searchString));
        } else if (searchOption.equals(SearchType.TITLE)) {
            currentBookList = bookManager.findByBookName(searchString);
        } else if (searchOption.equals(SearchType.ISBN)) {
            currentBookList = new ArrayList<>();
            Book book = bookManager.findBookByIsbn(searchString);
            if (book != null) {
                currentBookList.add(book);
            }
        }
        model.addAttribute("allBooks", currentBookList);
        return "pages/books.jsp";
    }


    /**
     * Retrieves the add page
     *
     * @return the name of the JSP page
     */
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
            HttpSession session) {

        logger.debug("Received request to show edit page");

//        ModelAndView mav = new ModelAndView();

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

        try {
            bookManager.saveNewBook(book);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }

        session.setAttribute("book", null);
        mav.addObject("book_added", "The book has been added");
        mav.setViewName("admin_pages/admin.jsp");
        return mav;
    }

    /**
     * Retrieves the edit page
     *
     * @return the name of the JSP page
     */
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
                               @RequestParam(value = "cover", required = false) CommonsMultipartFile locationMapFileData) {

        logger.debug("Received request to show edit page");

        book.setName(name);
        book.setIsbn(isbn);
        book.setGenre(genreManager.findByGenreName(genre));
        book.setPublisher(publisherManager.findByPublisherName(publisher));
        book.setAuthor(authorManager.findByAuthorName(author));
        book.setPageCount(pages);
        book.setPublishYear(year);
        book.setQuantity(quantity);
        book.setPrice(price);

        if (locationMapFileData.getBytes().length != 0) {
            book.setImage(locationMapFileData.getBytes());
        }

        try {
            bookManager.updateBook(book);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        return "pages/books.jsp";
    }

//    private void verifyIsbnUniqueness(String isbn) throws DuplicateException {
//        try {
//            if (bookManager.findBookByIsbn(isbn) != null) {
//                System.out.println("verifyIsbnUniqueness throws DuplEx");
//                throw new DuplicateException();
//            }
//        } catch (NoResultException ex) {
//            //ignore
//        }
//    }

    @ExceptionHandler(DuplicateException.class)
    public ModelAndView handleDuplicateException(ModelAndView mav) {
        System.out.println("handleDuplicateException is worked!");
        logger.error("Trying to write not unique isbn!");
//        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "This isbn is already exist");
        return mav;
    }


//    @ExceptionHandler(BadFileNameException.class)
//    public ModelAndView handleBadFileNameException(Exception ex) {
//        logger.error("IOException handler executed");
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("error", ex.getMessage());
//        return modelAndView;
//    }
}
