package com.tsystems.javaschool.view.controllers.validators;

import com.tsystems.javaschool.dao.entity.Genre;
import com.tsystems.javaschool.services.interfaces.GenreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.NoResultException;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 02.04.2016
 */
@Component
public class GenreValidator implements Validator {

    @Autowired
    private GenreManager genreManager;

    @Override
    public void validate(Object uploadedGenre, Errors errors) {
        Genre upGenre = (Genre) uploadedGenre;
        try {
            Genre sameGenre = genreManager.findByGenreName(upGenre.getName());
            if (sameGenre != null) {
                errors.rejectValue("name", "uploadForm.selectFile", "This genre is already exist!"); // name ????
            }
        } catch (NoResultException ex) {
            //ignored
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }
}
