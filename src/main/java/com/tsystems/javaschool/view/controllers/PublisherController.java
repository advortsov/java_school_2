package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Publisher;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.PublisherManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 02.04.2016
 */

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    private static Logger logger = Logger.getLogger(PublisherController.class);

    @Autowired
    PublisherManager publisherManager;

    private List<Publisher> createAllPublishersList() {
        return publisherManager.loadAllPublishers();
    }

    //   ---------------------- Publisher administrating ---------------------------------------------------
    @RequestMapping(value = "/edit_publisher", method = RequestMethod.POST)
    public String editPublisher(@ModelAttribute("publisher_name_new") String publisherName,
                                @ModelAttribute("publisher_for_edit") String publisherForEdit, HttpSession session) {

        logger.debug("Staring to edit publisher...");
        Publisher publisher = publisherManager.findByPublisherName(publisherForEdit);
        publisher.setName(publisherName);
        publisherManager.updatePublisher(publisher);
        session.setAttribute("allPublishersList", createAllPublishersList());
        logger.debug("Publisher has bean edited.");
        return "redirect:/admin#tab3";
    }

    @RequestMapping(value = "/delete_publisher", method = RequestMethod.POST)
    public String delPublisher(@ModelAttribute("publisher_name_del") String publisherName, HttpSession session) {
        Publisher publisher = publisherManager.findByPublisherName(publisherName);
        publisherManager.deletePublisher(publisher);
        session.setAttribute("allPublishersList", createAllPublishersList());
        return "redirect:/admin#tab3";
    }

    @RequestMapping(value = "/add_publisher", method = RequestMethod.POST)
    public String addPublisher(@ModelAttribute("publisher_name_add") String publisherName, HttpSession session) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherName);
        try {
            publisherManager.saveNewPublisher(publisher);
        } catch (DuplicateException e) {
            e.printStackTrace();
        }
        session.setAttribute("allPublishersList", createAllPublishersList());
        return "redirect:/admin#tab3";
    }

    @RequestMapping(value = "/ajaxPublisherValidation", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    public
    @ResponseBody
    String ajaxPublisherValidation(@RequestParam String publisherName) {
        Publisher publisher = null;
        try {
            publisher = publisherManager.findByPublisherName(publisherName);
            return "This publisher is already exists";
        } catch (NoResultException ex) {
            //ignore
        }
        return " ";
    }

}
