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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.javabegin.training.objects.UploadedBook;
import ru.javabegin.training.validators.BookValidator;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
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

        return "pages/books";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subView")
    public String getSubView(Model model) {
        System.out.println("getSubView            ");
        model.addAttribute("user", "Joe Dirt");
        model.addAttribute("time", new Date());
        return "pages/subView";
//        return new ModelAndView("pages/subView");
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
        return "pages/books";
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
        return "pages/books";
    }


    /**
     * Retrieves the add page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {
        logger.debug("Received request to show the book add page");

        // Create new Book and add to model
        // This is the formBackingOBject
        model.addAttribute("bookAttribute", new Book());

        // This will resolve to /WEB-INF/jsp/addpage.jsp
        return "admin_pages/admin";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addBook(
            @ModelAttribute("uploadedBook") UploadedBook uploadedBook,
            BindingResult result,
            @RequestParam("book_name") String name,
            @RequestParam("book_isbn") String isbn,
            @RequestParam("book_genre") String genre,
            @RequestParam("book_publisher") String publisher,
            @RequestParam("book_author") String author,
            @RequestParam("book_pages") int pages,
            @RequestParam("book_year") int year,
            @RequestParam("book_count") int quantity,
            @RequestParam("book_price") int price,
            @RequestParam(value = "cover", required = false)
            CommonsMultipartFile locationMapFileData) {

        logger.debug("Received request to show edit page");

//        verifyIsbnUniqueness(isbn);
        ModelAndView mav = new ModelAndView();
        bookValidator.validate(uploadedBook, result);
        if (result.hasErrors()) {
            mav.setViewName("admin_pages/admin");
        } else {
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

            if (locationMapFileData.getBytes().length != 0) {
                book.setImage(locationMapFileData.getBytes());
            }

            try {
                bookManager.saveNewBook(book);
            } catch (DuplicateException e) {
                e.printStackTrace();
            }

            mav.setViewName("admin_pages/admin");

        }

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
        // Retrieve existing Person and add to model
        // This is the formBackingOBject
        model.addAttribute("book", bookManager.findBookById(id));

        // This will resolve to /WEB-INF/jsp/editpage.jsp
        return "admin_pages/edit";
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
        return "pages/books";
    }

    private void verifyIsbnUniqueness(String isbn) throws DuplicateException {
        try {
            if (bookManager.findBookByIsbn(isbn) != null) {
                System.out.println("verifyIsbnUniqueness throws DuplEx");
                throw new DuplicateException();
            }
        } catch (NoResultException ex) {
            //ignore
        }
    }

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
