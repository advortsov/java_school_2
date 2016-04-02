package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.User;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 02.04.2016
 */
@Controller
@RequestMapping("/reg")
public class RegisterController {

    @Autowired
    private ClientManager clientManager;

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
        return "redirect:/login";

    }

    private String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @RequestMapping(value = "/ajaxLoginUniqValidation", method = RequestMethod.GET,
            produces = {"text/html; charset=UTF-8"})
    public @ResponseBody
    String ajaxLoginUniqValidation(@RequestParam String userLogin) {
        Client user = null;
        try {
            System.out.println("ajaxLoginUniqValidation = ");
            user = clientManager.findByUserName(userLogin);
            return "This user is already exists";
        } catch (NotRegisteredUserException ex) {
            //ignore
        }
        return " ";
    }
}
