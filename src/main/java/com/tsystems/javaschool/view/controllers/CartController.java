package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.enums.PaymentType;
import com.tsystems.javaschool.services.enums.ShippingType;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 22.02.2016
 */
@Controller
@RequestMapping("/cart")
@SessionAttributes("shoppingCart")
public class CartController {

    private static Logger logger = Logger.getLogger(CartController.class);

    @Autowired
    private BookManager bookManager;

    @Autowired
    private ShoppingCartManager cartManager;

    @Autowired
    private GenreManager genreManager;

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

    @ModelAttribute("shoppingCart")
    public ShoppingCart populateCart() {
        return cartManager.getShoppingCart();
    } // writing cart into session

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        Client client1 = (Client) session.getAttribute("client");
        System.out.println("client1 = " + client1);
        actualizeCart(request, resp, client1);
        return "pages/cart.jsp";
    }


    public void writeBooksIntoCookie(HttpServletRequest req, HttpServletResponse resp,
                                     long bookId, int previousQuantity) {
        logger.debug("Writing book into cookie...");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        deleteCurrentBookCookies(userName, bookId, req, resp);

        Cookie cookie = new Cookie(userName + String.valueOf(bookId),
                userName + "dlm" + bookId + "dlm" + previousQuantity);
        cookie.setMaxAge(60 * 60 * 24 * 30); // for working with browser closing
        resp.addCookie(cookie);
        logger.debug("Book has been added to cookie.");
    }

    public void deleteCurrentBookCookies(String userName, long bookId, HttpServletRequest req, HttpServletResponse resp) {
        // deleting the all user's cookies with the same book id
        logger.debug("Deleting the all user's cookies with the same book id...");
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                long id = Long.parseLong(cookieContent[1]);
                if (id == bookId && cookieContent[0].equals(userName)) {
                    cookie.setPath("cart/");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        logger.debug("All user's cookies with the same book id has been deleted.");
    }

    public void deleteCookiesByOwnerName(String userName, HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Deleting the all user's and guest's cookies...");
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                if (cookieContent[0].equals(userName) || cookieContent[0].equals("Guest")) {
                    cookie.setPath("cart/");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        logger.debug("All user's and guest's cookies has been deleted.");
    }

    @RequestMapping(value = "/clearCart", method = RequestMethod.GET)
    public String deleteAllBooksCookies(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        logger.debug("Deletes the all books cookies and empties client's shopping cart...");
        String cookieOwner = SecurityContextHolder.getContext().getAuthentication().getName();
        deleteCookiesByOwnerName(cookieOwner, req, resp);
        cartManager.clearCart();
        logger.debug("All books cookies and client's shopping cart content has been deleted...");
        return "pages/cart.jsp";
    }

    @RequestMapping(value = "/removeOrderLine", method = RequestMethod.GET)
    public String removeOrderLineAndCookie(@RequestParam(value = "id", required = true) long id,
                                           HttpServletRequest req, HttpServletResponse resp) {
        // deleting order line in cookies
        logger.debug("Deleting order line in cookies and in the cart...");

        Cookie[] cookies = req.getCookies();
        String cookieOwner = SecurityContextHolder.getContext().getAuthentication().getName();
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                if ((cookieContent[0].equals(cookieOwner) || cookieContent[0].equals("Guest"))
                        && id == Long.parseLong(cookieContent[1])) {
                    cookie.setPath("cart/");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }
        // deleting order line in the cart
        cartManager.removeLine(id);
        logger.debug("The order line in cookies and in the cart has been deleted.");
        return "pages/cart.jsp";
    }

    public void actualizeCart(HttpServletRequest request, HttpServletResponse resp, Client client) {
        logger.debug("Actualizing the client's cart...");

        ShoppingCart cart = cartManager.getShoppingCart();
        if (cart.getItems().isEmpty()) {
            fillUpFromCookies(cart, request, resp); // заполняем ее из кукисов
        }
        if (cart.getClient() == null) {
            cart.setClient(client);
        }
        cartManager.setShoppingCart(cart);
        populateCart();
        logger.debug("Client's cart has been  actualized...");

    } // Now we have a filled cart in the CartManager

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String addToCart(@RequestParam(value = "book_id", required = true) long id,
                            @ModelAttribute ShoppingCart shoppingCart,
                            HttpServletRequest req,
                            HttpServletResponse resp) {

        logger.debug("Adding book into client's cart...");

        List<OrderLine> orderLines = shoppingCart.getItems();
        Book newBook = bookManager.findBookById(id);
        OrderLine orderLineWithThisBook = null;

        if (!orderLines.isEmpty()) {
            for (OrderLine orderLine : orderLines) { // finding if this book is already in our order
                if (orderLine.getBook().getId() == newBook.getId()) {
                    orderLineWithThisBook = orderLine;
                }
            }
        }

        int previousQuantity;
        if (orderLineWithThisBook == null) {
            orderLines.add(new OrderLine(1, newBook));
            previousQuantity = 1;
        } else {
            previousQuantity = orderLineWithThisBook.getQuantity();
            for (OrderLine orderLine : orderLines) {
                if (orderLine.getBook().getId() == newBook.getId()) {
                    orderLine.setQuantity(++previousQuantity);
                }
            }
        }

        // every click on 'add to cart' link delete an old and create a new cookies
        shoppingCart.setItems(orderLines);
        cartManager.setShoppingCart(shoppingCart);
        writeBooksIntoCookie(req, resp, newBook.getId(), previousQuantity);

        logger.debug("Book has been added into client's cart...");

        return "pages/books.jsp";
    }


    public void fillUpFromCookies(ShoppingCart cart,
                                  HttpServletRequest request, HttpServletResponse resp) {
        logger.debug("Filling up client's cart from cookies...");

        Cookie[] cookies = request.getCookies();


        List<OrderLine> newItems = new ArrayList<>();
        // filling cart from cookies

        if (cookies != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String cookieOwner = auth.getName(); //get logged in username
            long id;
            int quantity;
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                if (value.contains("dlm")) {
                    String[] cookieContent = value.split("dlm");
                    if (cookieContent[0].equals(cookieOwner) || cookieContent[0].equals("Guest")) {
                        id = Long.parseLong(cookieContent[1]);
                        quantity = Integer.parseInt(cookieContent[2]);
                        if (cookieContent[0].equals("Guest")) {
                            deleteCookiesByOwnerName("Guest", request, resp);
                        }
                        newItems.add(new OrderLine(quantity, bookManager.findBookById(id)));
                    }
                }
            }
        }

        if (!newItems.isEmpty()) {
            cart.setItems(newItems);
        }

        logger.debug("Client's cart has been filled from cookies...");
    }

}
