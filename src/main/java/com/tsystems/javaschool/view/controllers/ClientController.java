package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 21.02.2016
 */

@Controller
@RequestMapping("/profile")
public class ClientController {

    private static Logger logger = Logger.getLogger(ClientController.class);

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private ClientManager clientManager;

    @ModelAttribute(value = "allGenresList")
    public List<Genre> createAllGenresList() {
        return genreManager.loadAllGenres();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "user_pages/profile.jsp";
    }

    @ModelAttribute(value = "clientOrdersList")
    public List<Order> createClientOrdersList(HttpSession session) {
        if (session.getAttribute("client") != null) {
            addDetails(session);
        }
        return clientManager.getClientOrders((Client) session.getAttribute("client"));
    }

    @ModelAttribute(value = "client")
    public Client addDetails(HttpSession session) {
        logger.debug("Adding client's details to session...");

        if (session.getAttribute("client") == null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userDetails.getUsername();

            Client client = null;
            try {
                client = clientManager.findByUserName(userName);
            } catch (NotRegisteredUserException ex) {
                client = new Client();
                client.setName("Guest");
            }
            session.setAttribute("client", client);
            return client;
        } else {
            logger.debug("Return client's details.");
            return (Client) session.getAttribute("client");
        }
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String saveEditClient(@RequestParam("client_name") String name,
                                 @RequestParam("client_surname") String surname,
                                 @RequestParam("client_address") String address,
                                 @RequestParam("client_bday") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date bday,
                                 @RequestParam("client_email") String email,
                                 HttpSession session) {

        logger.debug("Starting to edit client personal data...");

        Client client = (Client) session.getAttribute("client");

        client.setName(name);
        client.setSurname(surname);
        client.setAddress(address);
        client.setBirthday(new java.sql.Date(bday.getTime()));
        client.setEmail(email);

        clientManager.updateClient(client);

        logger.debug("Client personal data has been updated.");

        return "redirect:/profile";
    }


}
