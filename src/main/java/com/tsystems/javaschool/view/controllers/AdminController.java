package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.*;
import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 13.03.2016
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"allOrdersList", "allGenresList",
        "allAuthorsList", "allPublishersList", "topTenClients", "topTenBooks"})
public class AdminController {

    private static Logger logger = Logger.getLogger(BookListController.class);

    @Autowired
    private PublisherManager publisherManager;
    @Autowired
    private AuthorManager authorManager;
    @Autowired
    private GenreManager genreManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private AdminManager adminManager;
//---------------------------past into context------------------
    @ModelAttribute(value = "allOrdersList")
    public List<Order> createAllOrdersList() {
        return orderManager.loadAllOrders();
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

    @ModelAttribute(value = "topTenClients")
    public Map<Client, Integer> createTopTenClientsMap() {
        return adminManager.getTopTenClients();
    }

    @ModelAttribute(value = "topTenBooks")
    public Map<Book, Integer> createTopTenBooksMap() {
        return adminManager.getTopTenBooks();
    }
    //-----------------------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "admin_pages/admin.jsp";
    }

    @RequestMapping(value = "/proceed", method = RequestMethod.POST)
    public String getProceedsPerPeriod(@RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                      @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Model model) {
        logger.debug("Try to get orders per period");
        List<Order> ordersPerPeriod = adminManager.getOrdersPerPeriod(startDate, endDate);
        int totalSumm = 0;
        for (Order order : ordersPerPeriod) {
            totalSumm += order.getTotalSumm();
        }
        model.addAttribute("ordersPerPeriod", ordersPerPeriod);
        return "admin_pages/admin.jsp";
//        return "forward:/admin#tab7";
    }




//   ---------------------- Genre administrating ---------------------------------------------------

    @RequestMapping(value = "/edit_genre", method = RequestMethod.POST)
    public String saveEditGenre(@ModelAttribute("genre_name_new") String genreName,
                                @ModelAttribute("genre_name_ed") String genreForEdit, Model model) {
        Genre genre = genreManager.findByGenreName(genreForEdit);
        genre.setName(genreName);
        genreManager.updateGenre(genre);
        model.addAttribute("allGenresList", createAllGenresList());
        return "redirect:/admin#tab2";
    }

    @RequestMapping(value = "/delete_genre", method = RequestMethod.POST)
    public String delGenre(@ModelAttribute("genre_name_del") String genreName, Model model) {
        Genre genre = genreManager.findByGenreName(genreName);
        genreManager.deleteGenre(genre);
        model.addAttribute("allGenresList", createAllGenresList());
        return "redirect:/admin#tab2";
}

    @RequestMapping(value = "/add_genre", method = RequestMethod.POST)
    public String addGenre(@ModelAttribute("add_genre_name") String genreName, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin_pages/admin.jsp";
        }
        Genre genre = new Genre();
        genre.setName(genreName);
        try {
            genreManager.saveNewGenre(genre);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        model.addAttribute("allGenresList", createAllGenresList());
        return "redirect:/admin#tab2";
    }


    @RequestMapping(value = "/ajaxGenreValidation", method = RequestMethod.GET,
            produces = {"text/html; charset=UTF-8"})
    public @ResponseBody String ajaxGenreValidation(@RequestParam String genreName) {
        Genre genre = null;
        try {
            System.out.println("ajaxGenreValidation = exists!");
            genre = genreManager.findByGenreName(genreName);
            return "This genre is already exists";
        } catch (NoResultException ex){
            //ignore
        }
        System.out.println("return ok");

        return " ";
    }

    @RequestMapping(value = "/ajaxAuthorValidation", method = RequestMethod.GET,
            produces = {"text/html; charset=UTF-8"})
    public @ResponseBody String ajaxAuthorValidation(@RequestParam String authorName) {
        Author author = null;
        try {
            author = authorManager.findByAuthorName(authorName);
            return "This author is already exists";
        } catch (NoResultException ex){
            //ignore
        }
        return " ";
    }

    @RequestMapping(value = "/ajaxPublisherValidation", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    public @ResponseBody String ajaxPublisherValidation(@RequestParam String publisherName) {
        Publisher publisher = null;
        try {
            publisher = publisherManager.findByPublisherName(publisherName);
            return "This publisher is already exists";
        } catch (NoResultException ex){
            //ignore
        }
        return " ";
    }

    // ----------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------
//   ---------------------- Publisher administrating ---------------------------------------------------
    @RequestMapping(value = "/edit_publisher", method = RequestMethod.POST)
    public String editPublisher(@ModelAttribute("publisher_name_new") String publisherName,
                                @ModelAttribute("publisher_for_edit") String publisherForEdit, Model model) {

        Publisher publisher = publisherManager.findByPublisherName(publisherForEdit);
        publisher.setName(publisherName);
        publisherManager.updatePublisher(publisher);
        model.addAttribute("allPublishersList", createAllPublishersList());
        return "redirect:/admin#tab3";
    }

    @RequestMapping(value = "/delete_publisher", method = RequestMethod.POST)
    public String delPublisher(@ModelAttribute("publisher_name_del") String publisherName, Model model) {
        Publisher publisher = publisherManager.findByPublisherName(publisherName);
        publisherManager.deletePublisher(publisher);
        model.addAttribute("allPublishersList", createAllPublishersList());
        return "redirect:/admin#tab3";
    }

    @RequestMapping(value = "/add_publisher", method = RequestMethod.POST)
    public String addPublisher(@ModelAttribute("publisher_name_add") String publisherName, Model model) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherName);
        try {
            publisherManager.saveNewPublisher(publisher);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        model.addAttribute("allPublishersList", createAllPublishersList());
        return "redirect:/admin#tab3";
    }
    // ----------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------
//   ---------------------- Author administrating ---------------------------------------------------
    @RequestMapping(value = "/edit_author", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute("author_name_new") String authorName,
                             @ModelAttribute("author_for_edit") String authorForEdit, Model model) {

        Author author = authorManager.findByAuthorName(authorForEdit);
        author.setName(authorName);
        authorManager.updateAuthor(author);
        model.addAttribute("allAuthorsList", createAllAuthorsList());
        return "redirect:/admin#tab4";
    }

    @RequestMapping(value = "/delete_author", method = RequestMethod.POST)
    public String delAuthor(@ModelAttribute("author_name_del") String authorName, Model model) {

        Author author = authorManager.findByAuthorName(authorName);
        authorManager.deleteAuthor(author);
        model.addAttribute("allAuthorsList", createAllAuthorsList());
        return "redirect:/admin#tab4";
    }

    @RequestMapping(value = "/add_author", method = RequestMethod.POST)
    public String addAuthor(@ModelAttribute("author_name_add") String authorName, Model model) {
        Author author = new Author();
        author.setName(authorName);
        try {
            authorManager.saveNewAuthor(author);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        model.addAttribute("allAuthorsList", createAllAuthorsList());
        return "redirect:/admin#tab4";
    }
    // ----------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------


    @RequestMapping(value = "/edit_order", method = RequestMethod.GET)
    public String getEdit(@RequestParam(value = "id", required = true) long id,
                          Model model) {
        logger.debug("Received request to show order edit page");
        System.out.println("orderManager.findOrderById(id) = " + orderManager.findOrderById(id).getId());
        model.addAttribute("id", id);
        model.addAttribute("orderStatusList", OrderStatus.values());
        return "admin_pages/edit_order.jsp";
    }


    @RequestMapping(value = "/edit_order", method = RequestMethod.POST)
    public String saveEditOrder(
            @RequestParam("order_status") String orderStatus,
            @RequestParam(value = "id", required = true) long id,
            Model model) {

        Order orderForEdit = orderManager.findOrderById(id);
        OrderStatus newOrderStatus = OrderStatus.valueOf(orderStatus);
        orderForEdit.setOrderStatus(newOrderStatus);
        orderManager.updateOrder(orderForEdit);
        model.addAttribute("allOrdersList", createAllOrdersList());

        return "redirect:/admin#tab5";
    }
}
