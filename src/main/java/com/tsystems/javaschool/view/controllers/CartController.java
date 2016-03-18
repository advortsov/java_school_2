package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    ShoppingCartManager cartManager; // DONT WORK!

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, Model model) {
        Client client = clientController.actualizeClient(request, "Guest");
        actualizeCart(request, client); //1
        return "pages/cart";
    }

    public void actualizeCart(HttpServletRequest request, Client client) {
        ShoppingCart cart = cartManager.getShoppingCart();
        System.out.println(cart.getItems().size());
        //fillUpFromCookies(cart, request); // заполняем ее из кукисов
        System.out.println(cart.getItems().size());
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
                            Model model,
                            HttpServletRequest req,
                            HttpServletResponse resp) {

        System.out.println("shoppingCart start method addtocart = " + shoppingCart.getItems().size());

        System.out.println(shoppingCart);
        System.out.println("addToCart cart size: " + shoppingCart.getItems().size());
        List<OrderLine> orderLines = new ArrayList<>();
        if (shoppingCart.getItems() != null){
            orderLines = shoppingCart.getItems();
        }

        Book newBook = bookManager.findBookById(id);
        System.out.println("newBook " + newBook);
        OrderLine orderLineWithThisBook = null;

        if (!orderLines.isEmpty()) {
            for (OrderLine orderLine : orderLines) { // ищем, есть ли уже такая книга в заказе
                if (orderLine.getBook().equals(newBook)) {
                    orderLineWithThisBook = orderLine;
                }
            }
        }

        int previousQuantity = 0;
        if (orderLineWithThisBook == null) {
            orderLines.add(new OrderLine(1, newBook));
        } else {
            previousQuantity = orderLineWithThisBook.getQuantity();
            for (OrderLine orderLine : orderLines) {
                if (orderLine.getBook().equals(newBook)) {
                    orderLine.setQuantity(++previousQuantity);
                }
            }
        }

        // при каждом клике на добавить в корзину затираются старые и создаются новые куки:
        shoppingCart.setItems(orderLines);

        cartManager.setShoppingCart(shoppingCart);
        System.out.println("shoppingCart finish method addtocart = " + cartManager.getShoppingCart().getItems().size());

//        req.setAttribute("cartManager", cartManager);
        //writeBooksIntoCookie(req, resp, newBook.getId(), previousQuantity);

        return "pages/books";
    }

    @RequestMapping(value = "/removeOrderLine", method = RequestMethod.GET)
    public String deleteOrderLineCookie(@RequestParam(value = "id", required = true) long id,
                                        HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        String cookieOwner = (String) req.getSession().getAttribute("name_for_greeting");
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                if ((cookieContent[0].equals(cookieOwner) || cookieContent[0].equals("Guest"))
                        && id == Long.parseLong(cookieContent[1])) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                }
            }
        }
        return "pages/cart";
    }


    public void fillUpFromCookies(ShoppingCart cart,
                                  HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        List<OrderLine> newItems = new ArrayList<>();
        // заполняем по куки корзину

        if (cookies != null) {
            String cookieOwner = (String) request.getSession().getAttribute("name_for_greeting");
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

    public static void writeBooksIntoCookie(HttpServletRequest req, HttpServletResponse resp,
                                            long bookId, int previousQuantity) {
        deleteExistingCookies(bookId, req);
        Cookie cookie = new Cookie(String.valueOf(bookId),
                req.getSession().getAttribute("name_for_greeting") + "dlm" + bookId + "dlm" + ++previousQuantity);
        cookie.setMaxAge(60 * 60 * 24 * 30); // for working with browser closing
        resp.addCookie(cookie);
    }

    public static void deleteCartsBooksCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        String cookieOwner = (String) req.getSession().getAttribute("name_for_greeting");
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("dlm")) {
                String[] cookieContent = value.split("dlm");
                if (cookieContent[0].equals(cookieOwner) || cookieContent[0].equals("Guest")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                }
            }
        }
    }

    public static void deleteExistingCookies(long bookId, HttpServletRequest req) {
        // удаляем куки с таким же айди, чтобы перезаписать количество
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String value = cookie.getValue();
            if (value.contains("qty")) {
                String[] arr = value.split("qty");
                long id = Long.parseLong(arr[0]);
                if (id == bookId) {
                    cookie.setMaxAge(0);
                }
            }
        }
    }



}
