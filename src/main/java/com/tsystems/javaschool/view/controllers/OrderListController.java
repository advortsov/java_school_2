package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.enums.PaymentStatus;
import com.tsystems.javaschool.services.enums.PaymentType;
import com.tsystems.javaschool.services.enums.ShippingType;
import com.tsystems.javaschool.services.exception.EmptyOrderException;
import com.tsystems.javaschool.services.exception.NotEnoughBooksInTheStockException;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.OrderManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javabegin.training.objects.CreatedOrder;
import ru.javabegin.training.validators.OrderValidator;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class OrderListController {

    @Autowired
    OrderManager orderManager;

    @Autowired
    ShoppingCartManager shoppingCartManager;

    @Autowired
    CartController cartController;

    @Autowired
    BookManager bookManager;

    @Autowired
    private OrderValidator orderValidator;

    /**
     * Puts in the shopping cart content of the last order (additional task)
     */
    @RequestMapping(value = "/repeatOrder", method = RequestMethod.POST)
    public String repeatOrder(@RequestParam(value = "id", required = true) long id) {
        orderManager.repeatOrder(id);
        return "pages/cart.jsp";
    }


    @RequestMapping(value = "/ajaxBooksQuantityValidation", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    public @ResponseBody
    String ajaxBooksQuantityValidation(@RequestParam int bookId, @RequestParam int bookQuantity) {
        System.out.println("WORKED!");

        if (bookQuantity > bookManager.getBookQuantity(bookId)) {
            System.out.println("YES ITS BIGGER!");
            return "Not enough books '" + bookManager.findBookById(bookId).getName() + "' in stock. Please, choose less number.";
        }
        return " ";
    }

    @RequestMapping(value = "/create-order", method = RequestMethod.POST)
    public ModelAndView createOrder(
            @ModelAttribute("createdOrder") Order createdOrder,
            BindingResult result,
            HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws EmptyOrderException, NotEnoughBooksInTheStockException {

        System.out.println("createdOrder = " + createdOrder);
        List<OrderLine> orderLines = shoppingCartManager.getShoppingCart().getItems();
        createdOrder.setOrderLines(orderLines);

        // заданное на прошлой форме количество перезаписываем в каждом ордерлайне
        Order order = new Order();
        for (OrderLine orderLine : orderLines) {
            long id = orderLine.getBook().getId();
            int wantedQuantity = Integer.parseInt(req.getParameter("q-" + id));
            System.out.println("wantedQuantity = " + wantedQuantity);
//            orderValidator.validate(createdOrder, result);

            if (wantedQuantity > bookManager.getBookQuantity(orderLine.getBook().getId())) {
//                System.out.println("rejectValue = = = ");
//                result.rejectValue("orderLines", "Not enough books '" + orderLine.getBook().getName() + "' in stock. Please, choose more less number.");
//                result.addError(new ObjectError("orderLines", "Not enough books '" + orderLine.getBook().getName() + "' in stock. Please, choose more less number."));
//
                throw new NotEnoughBooksInTheStockException(wantedQuantity);
            }

            if (result.hasErrors()) {
                mav.setViewName("pages/cart.jsp");
            } else {
                orderLine.setQuantity(wantedQuantity);
                orderLine.setOrder(order);
            }
        }


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

        orderManager.saveNewOrder(order);

        String userName = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().toString();
        cartController.deleteAllBooksCookies(userName, req, resp);

        mav.setViewName("pages/cart.jsp");
        return mav;
    }

}


