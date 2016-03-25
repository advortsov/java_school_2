package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ShoppingCartManager cartManager;

    @Autowired
    private ClientManager clientManager;

    @RequestMapping(value = "/addDetails", method = RequestMethod.GET)
    public String addDetails(@RequestParam(value = "error", required = false) String error,
                                   HttpSession session) {
        // erase cart for giving the clear cart to the next auth user
        cartManager.clearCart();

        logger.debug("Adding details to user");

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();

        Client client = null;
        try {
            client = clientManager.findByUserName(userName);
        } catch (NotRegisteredUserException ex) {
            client = new Client();
            client.setName("Guest");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("userName", client.getName());
        session.setAttribute("client", client);
        // теперь у нас в сессии есть наш клиент из базы или подложка для анонимуса

//        mav.setViewName("pages/books.jsp");
//        return mav;
        return  "forward:/books";
    }


    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView goLogin(@RequestParam(value = "error", required = false) String error, ModelAndView mav) {
        logger.debug("Returning login.jsp page");
        mav.setViewName("login.jsp");

        if (error != null) {
            mav.addObject("error", "Invalid username or password");
        }
        return mav;
    }
}
