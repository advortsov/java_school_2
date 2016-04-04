package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.03.2016
 */

@Controller
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private GenreManager genreManager;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm() {
        return "pages/books.jsp";
    }


//   ---------------------- Genre administrating ---------------------------------------------------

    @RequestMapping(value = "/edit_genre", method = RequestMethod.POST)
    public String saveEditGenre(@ModelAttribute("genre_name_new") String genreName,
                                @ModelAttribute("genre_name_ed") String genreForEdit, HttpSession session) {
        Genre genre = genreManager.findByGenreName(genreForEdit);
        genre.setName(genreName);
        genreManager.updateGenre(genre);
        session.setAttribute("allGenresList", genreManager.loadAllGenres());
        return "redirect:/admin#tab2";
    }

    @RequestMapping(value = "/delete_genre", method = RequestMethod.POST)
    public String delGenre(@ModelAttribute("genre_name_del") String genreName, HttpSession session) {
        Genre genre = genreManager.findByGenreName(genreName);
        genreManager.deleteGenre(genre);
        session.setAttribute("allGenresList", genreManager.loadAllGenres());
        return "redirect:/admin#tab2";
    }

    @RequestMapping(value = "/add_genre", method = RequestMethod.POST)
    public String addGenre(@ModelAttribute("add_genre_name") String genreName,
                           BindingResult result,
                           HttpSession session,
                           ModelAndView mav) throws DuplicateException {

        Genre genre = new Genre();
        genre.setName(genreName);

//        genreValidator.validate(genre, result);
//
//        if (result.hasErrors()) {
//            session.setAttribute("genre", genre);
////            mav.setViewName("admin_pages/admin.jsp");
////            return mav;
//            return "forward:/admin#tab2";
//        }


        genreManager.saveNewGenre(genre);

        session.setAttribute("allGenresList", genreManager.loadAllGenres());
        return "redirect:/admin#tab2";
    }

    @RequestMapping(value = "/ajaxGenreValidation", method = RequestMethod.GET,
            produces = {"text/html; charset=UTF-8"})
    public
    @ResponseBody
    String ajaxGenreValidation(@RequestParam String genreName) {
        Genre genre = null;
        try {
            genre = genreManager.findByGenreName(genreName);
            return "This genre is already exists";
        } catch (NoResultException ex) {
            //ignore
        }
        return " ";
    }

}
