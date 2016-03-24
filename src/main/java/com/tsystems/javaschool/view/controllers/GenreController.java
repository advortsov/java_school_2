package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.03.2016
 */

@Controller
//@Component
@RequestMapping("/genre-module")
//@SessionAttributes("genres")
public class GenreController {

    @Autowired
    GenreManager genreManager;


//    @ModelAttribute("allGenres")
//    public List<Genre> allGenres() {
//        List<Genre> resultList = genreManager.loadAllGenres();
//        return resultList;
//    }

    //@ModelAttribute("allGenres")
    public List<Genre> getAllGenres() {
        return genreManager.loadAllGenres();
    }

    /**
     * Method will be called in initial page load at GET /genre-module
     */
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model) {
        return "pages/books.jsp";
    }



}
