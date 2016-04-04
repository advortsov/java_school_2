package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.*;
import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.enums.PaymentStatus;
import com.tsystems.javaschool.services.enums.ShippingType;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
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

    private static Logger logger = Logger.getLogger(AdminController.class);

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

    //-------------------------------------------
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

    @RequestMapping(value = "/Address", method = RequestMethod.POST)
    public String getProceedPerPeriod(@RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                       @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                       Date endDate, Model model, HttpSession session) {
        logger.debug("Try to get orders per period");
        List<Order> ordersPerPeriod = adminManager.getOrdersPerPeriod(startDate, endDate);
        int totalSumm = 0;
        for (Order order : ordersPerPeriod) {
            totalSumm += order.getTotalSumm();
        }
        session.setAttribute("ordersPerPeriod", ordersPerPeriod);
        session.setAttribute("AddressPerPeriod", totalSumm);
        session.setAttribute("startDate", startDate);
        session.setAttribute("endDate", endDate);
        return "redirect:/admin#tab7";
    }

// ----------------------------------------------------------------------------------------------------------


    @RequestMapping(value = "/edit_order", method = RequestMethod.GET)
    public String getEdit(@RequestParam(value = "id", required = true) long id,
                          Model model) {
        logger.debug("Received request to show order edit page");
        model.addAttribute("id", id);
        model.addAttribute("orderStatusList", OrderStatus.values());
        model.addAttribute("paymentStatusList", PaymentStatus.values());
        model.addAttribute("shippingTypeList", ShippingType.values());
        return "admin_pages/edit_order.jsp";
    }


    @RequestMapping(value = "/edit_order", method = RequestMethod.POST)
    public String saveEditOrder(
            @RequestParam(value = "order_status", required = false) String orderStatus,
            @RequestParam(value = "payment_status", required = false) String paymentStatus,
            @RequestParam(value = "shipping_type", required = false) String shippingType,
            @RequestParam(value = "id", required = true) long id,
            Model model) {

        Order orderForEdit = orderManager.findOrderById(id);

        OrderStatus newOrderStatus = OrderStatus.valueOf(orderStatus);
        PaymentStatus newPaymentStatus = PaymentStatus.valueOf(paymentStatus);
        ShippingType newShippingType = ShippingType.valueOf(shippingType);

        if (orderStatus != null)
            orderForEdit.setOrderStatus(newOrderStatus);

        if (paymentStatus != null)
            orderForEdit.setPaymentStatus(newPaymentStatus);

        if (shippingType != null)
            orderForEdit.setShippingType(newShippingType);

        orderManager.updateOrder(orderForEdit);
        model.addAttribute("allOrdersList", createAllOrdersList());

        return "redirect:/admin#tab5";
    }
}
