package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.User;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private ClientManager clientManager;


    @ModelAttribute(value = "clientOrdersList")
    public List<Order> createClientOrdersList(HttpSession session) {
        if (session.getAttribute("client") != null) {
            return clientManager.getClientOrders((Client) session.getAttribute("client"));
        } else {
            return null;
        }
    }

    //    ajaxLoginUniqValidation
    @RequestMapping(value = "/ajaxLoginUniqValidation", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    public
    @ResponseBody
    String ajaxLoginUniqValidation(@RequestParam String login) {
        Client user = null;
        try {
            System.out.println("ajaxLoginUniqValidation = ");
            user = clientManager.findByUserName(login);
            return "This user is already exists";
        } catch (NotRegisteredUserException ex) {
            //ignore
        }
        return " ";
    }

    public Client actualizeClient(HttpServletRequest request, String userName) {
        Client client = null;
        try {
            client = clientManager.findByUserName(userName);
        } catch (NotRegisteredUserException ex) {
            client = new Client();
            client.setName("Guest");
        }

        request.getSession().setAttribute("currentClient", client);
        // теперь у нас в сессии есть наш клиент из базы или подложка для анонимуса

        return client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "user_pages/profile.jsp";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "pages/register.jsp";
    }

    @RequestMapping(value = "/register_new", method = RequestMethod.POST)
    public String saveNewClient(@RequestParam("client_name") String name,
                                @RequestParam("client_surname") String surname,
                                @RequestParam("client_address") String address,
                                @RequestParam("client_bday") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date bday,
                                @RequestParam("client_user_name") String username,
                                @RequestParam("client_password") String password,
                                @RequestParam("client_email") String email) {

        Client client = new Client();

        client.setName(name);
        client.setSurname(surname);
        client.setAddress(address);
        client.setBirthday(new java.sql.Date(bday.getTime()));
        client.setEmail(email);

        User user = new User();

        UserRole userRole = clientManager.getUserRoleByName("ROLE_USER");

        user.setUserName(username);
        user.setUserPass(encrypt(password));
        user.setUserRole(userRole);

        client.setUser(user);

        clientManager.updateClient(client);
        return "forward:login.jsp";

    }

    private String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String saveEditClient(@RequestParam("client_name") String name,
                                 @RequestParam("client_surname") String surname,
                                 @RequestParam("client_address") String address,
                                 @RequestParam("client_bday") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date bday,
                                 @RequestParam("client_email") String email,
                                 HttpSession session) {

//        logger.debug("Received request to show edit page");

        Client client = (Client) session.getAttribute("client");

        client.setName(name);
        client.setSurname(surname);
        client.setAddress(address);
        client.setBirthday(new java.sql.Date(bday.getTime()));
        client.setEmail(email);

        clientManager.updateClient(client);
        return "forward:user_pages/profile.jsp";
    }


}
