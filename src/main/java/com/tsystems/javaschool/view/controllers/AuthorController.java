package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Author;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.AuthorManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 02.04.2016
 */

@Controller
@RequestMapping("/author")
public class AuthorController {

    private static Logger logger = Logger.getLogger(AuthorController.class);

    @Autowired
    AuthorManager authorManager;


    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "admin_pages/admin.jsp";
    }


    //   ---------------------- Author administrating ---------------------------------------------------
    @RequestMapping(value = "/edit_author", method = RequestMethod.POST)
    public String editAuthor(@ModelAttribute("author_name_new") String authorName,
                             @ModelAttribute("author_for_edit") String authorForEdit, HttpSession session) {

        Author author = authorManager.findByAuthorName(authorForEdit);
        author.setName(authorName);
        authorManager.updateAuthor(author);
        session.setAttribute("allAuthorsList", authorManager.loadAllAuthors());
        return "redirect:/admin#tab4";
    }

    @RequestMapping(value = "/delete_author", method = RequestMethod.POST)
    public String delAuthor(@ModelAttribute("author_name_del") String authorName, HttpSession session) {

        Author author = authorManager.findByAuthorName(authorName);
        authorManager.deleteAuthor(author);
        session.setAttribute("allAuthorsList", authorManager.loadAllAuthors());
        return "redirect:/admin#tab4";
    }

    @RequestMapping(value = "/add_author", method = RequestMethod.POST)
    public String addAuthor(@ModelAttribute("author_name_add") String authorName, HttpSession session) throws DuplicateException {
        Author author = new Author();
        author.setName(authorName);
        authorManager.saveNewAuthor(author);
        session.setAttribute("allAuthorsList", authorManager.loadAllAuthors());
        return "redirect:/admin#tab4";
    }
    // ----------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/ajaxAuthorValidation", method = RequestMethod.GET,
            produces = {"text/html; charset=UTF-8"})
    public
    @ResponseBody
    String ajaxAuthorValidation(@RequestParam String authorName) {
        Author author = null;
        try {
            author = authorManager.findByAuthorName(authorName);
            return "This author is already exists";
        } catch (NoResultException ex) {
            //ignore
        }
        return " ";
    }

}
