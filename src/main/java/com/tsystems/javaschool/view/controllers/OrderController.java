package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.enums.PaymentStatus;
import com.tsystems.javaschool.services.enums.PaymentType;
import com.tsystems.javaschool.services.enums.ShippingType;
import com.tsystems.javaschool.services.exception.EmptyOrderException;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import com.tsystems.javaschool.services.interfaces.OrderManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import com.tsystems.javaschool.view.controllers.validators.OrderValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private ShoppingCartManager shoppingCartManager;

    @Autowired
    private CartController cartController;

    @Autowired
    private BookManager bookManager;

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private OrderValidator orderValidator;

    @ModelAttribute(value = "allGenresList")
    public List<Genre> createAllGenresList() {
        return genreManager.loadAllGenres();
    }

    @ModelAttribute(value = "shippingTypeList")
    public ShippingType[] createShippingTypeList() {
        return ShippingType.values();
    }

    @ModelAttribute(value = "paymentTypeList")
    public PaymentType[] createPaymentTypeList() {
        return PaymentType.values();
    }

    /**
     * Puts in the shopping cart content of the last order (additional task)
     */
    @RequestMapping(value = "/repeatOrder", method = RequestMethod.GET)
    public String repeatOrder(@RequestParam(value = "id", required = true) long id) {
        orderManager.repeatOrder(id);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/ajaxBooksQuantityValidation", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    public
    @ResponseBody
    String ajaxBooksQuantityValidation(@RequestParam int bookId, @RequestParam int bookQuantity) {
        if (bookQuantity > bookManager.getBookQuantity(bookId)) {
            return "Not enough books '" + bookManager.findBookById(bookId).getName() + "' in stock. Please, choose less number.";
        }
        return " ";
    }

    @RequestMapping(value = "/create-order", method = RequestMethod.POST)
    public ModelAndView createOrder(
            @ModelAttribute("createdOrder") Order createdOrder,
            BindingResult result,
            HttpServletRequest req, HttpServletResponse resp, HttpSession session, ModelAndView mav) throws Exception {
        logger.debug("Creating new order...");

        List<OrderLine> orderLines = shoppingCartManager.getShoppingCart().getItems();
        createdOrder.setOrderLines(orderLines);

        // rewrite books count from last form
        Order order = new Order();
        for (OrderLine orderLine : orderLines) {
            long id = orderLine.getBook().getId();
            int wantedQuantity = Integer.parseInt(req.getParameter("q-" + id));
            orderLine.setQuantity(wantedQuantity);
            orderLine.setBookIsbn(orderLine.getBook().getIsbn());
            orderLine.setBookActualPrice(orderLine.getBook().getPrice());
            orderLine.setOrder(order);
        }

        mav.setViewName("pages/cart.jsp");

        if (orderLines.isEmpty()) throw new EmptyOrderException();

        List<OrderLine> copy = new ArrayList<>();
        for (OrderLine line : orderLines) {
            copy.add(line);
        }

        order.setOrderLines(copy);
        order.setShippingType(ShippingType.valueOf(req.getParameter("shipping_type")));
        order.setPaymentType(PaymentType.valueOf(req.getParameter("payment_type")));
        order.setPaymentStatus(PaymentStatus.WAITING_FOR_PAYMENT); // потому что заказ только что создан
        order.setOrderStatus(OrderStatus.WAITING_FOR_PAYMENT); // потому что заказ только что создан
        order.setClient((Client) req.getSession().getAttribute("client"));
        order.setDate(new Date(System.currentTimeMillis()));

        orderValidator.validate(order, result);

        if (result.hasErrors()) {
            session.setAttribute("order", order);
            return mav;
        }

        orderManager.saveNewOrder(order);
        cartController.deleteAllBooksCookies(req, resp);
        mav.addObject("success", "Your order is processed!");

        logger.debug("New order has been created.");

        return mav;
    }


}


