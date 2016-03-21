package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    BookManager bookManager;

    @Autowired
    ClientController clientController;

    @Autowired
    ShoppingCartManager cartManager;

    public static void writeBooksIntoCookie(HttpServletRequest req, HttpServletResponse resp,
                                            long bookId, int previousQuantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username
        deleteCurrentBookCookies(userName, bookId, req, resp);

        Cookie cookie = new Cookie(String.valueOf(bookId),
                userName + "dlm" + bookId + "dlm" + previousQuantity);
        cookie.setMaxAge(60 * 60 * 24 * 30); // for working with browser closing
        resp.addCookie(cookie);
    }

    public static void deleteCurrentBookCookies(String userName, long bookId, HttpServletRequest req, HttpServletResponse resp) {
        // удаляем куки с таким же айди, чтобы перезаписать количество
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
    }

    @RequestMapping(value = "/clearCart", method = RequestMethod.GET)
    public String deleteAllBooksCookies(String cookieOwner, HttpServletRequest req, HttpServletResponse resp) {
        //delete all books cookies
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                if (cookieContent[0].equals(cookieOwner) || cookieContent[0].equals("Guest")) {
                    cookie.setPath("cart/");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                }
            }
        }

        // to empty cart
        cartManager.clearCart();
        return "pages/cart";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request) {
        Client client = clientController.actualizeClient(request, "Guest");
        actualizeCart(request, client); //1
        return "pages/cart";
    }

    public void actualizeCart(HttpServletRequest request, Client client) {
        ShoppingCart cart = cartManager.getShoppingCart();
        fillUpFromCookies(cart, request); // заполняем ее из кукисов
        cart.setClient(client);
        cartManager.setShoppingCart(cart);
        populateCart();
    } // В менеджере теперь есть норм карта заполненная из кукисов

    @ModelAttribute("shoppingCart")
    public ShoppingCart populateCart() {
        System.out.println("populateCart = " + cartManager.getShoppingCart().getItems().size());
        return cartManager.getShoppingCart();
    } // записываем карту в сессию

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public String addToCart(@RequestParam(value = "book_id", required = true) long id,
                            @ModelAttribute ShoppingCart shoppingCart,
                            HttpServletRequest req,
                            HttpServletResponse resp) {

        List<OrderLine> orderLines = shoppingCart.getItems();
        Book newBook = bookManager.findBookById(id);
        OrderLine orderLineWithThisBook = null;

        if (!orderLines.isEmpty()) {
            for (OrderLine orderLine : orderLines) { // ищем, есть ли уже такая книга в заказе
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

        // при каждом клике на добавить в корзину затираются старые и создаются новые куки:
        shoppingCart.setItems(orderLines);

        cartManager.setShoppingCart(shoppingCart);

//        req.setAttribute("cartManager", cartManager);
        writeBooksIntoCookie(req, resp, newBook.getId(), previousQuantity);

        return "pages/books";
    }

    @RequestMapping(value = "/removeOrderLine", method = RequestMethod.GET)
    public String removeOrderLineAndCookie(@RequestParam(value = "id", required = true) long id,
                                           HttpServletRequest req, HttpServletResponse resp) {
        // удаляем строку в куках
        Cookie[] cookies = req.getCookies();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cookieOwner = auth.getName(); //get logged in username
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
        // удаляем строку в корзине
        cartManager.removeLine(id);
        return "pages/cart";
    }

    public void fillUpFromCookies(ShoppingCart cart,
                                  HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        List<OrderLine> newItems = new ArrayList<>();
        // заполняем по куки корзину

        if (cookies != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String cookieOwner = auth.getName(); //get logged in username
            System.out.println("cookieOwner = " + cookieOwner);
            long id;
            int quantity;
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                if (value.contains("dlm")) {
                    String[] cookieContent = value.split("dlm");
                    if (cookieContent[0].equals(cookieOwner)) {
                        id = Long.parseLong(cookieContent[1]);
                        quantity = Integer.parseInt(cookieContent[2]);
                        newItems.add(new OrderLine(quantity, bookManager.findBookById(id)));
                    }
                }
            }
        }

        if (!newItems.isEmpty()) {
            cart.setItems(newItems);
        }

    }


}
