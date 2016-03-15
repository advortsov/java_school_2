package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
public class CartController {

    @Autowired
    BookManager bookManager;

    @Autowired
    ShoppingCartManager cartManager;


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

    public static void deleteOrderLineCookie(long id,
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


    public void actualizeCart(HttpServletRequest request, Client client) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setClient(client);
        cartManager.setShoppingCart(shoppingCart);
        fillUpFromCookies(shoppingCart, request); // заполняем ее из кукисов
        request.getSession().setAttribute("cart", shoppingCart);
    }

}
