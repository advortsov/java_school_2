package com.tsystems.javaschool.view.controllers.validators;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.NoResultException;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 22.03.2016
 */

@Component
public class BookValidator implements Validator {

    private static final Logger logger = Logger.getLogger(BookValidator.class);

    @Autowired
    private BookManager bookManager;

    @Override
    public void validate(Object uploadedBook, Errors errors) {
        logger.debug("Starting to validate book creating/editing...");
        Book upBook = (Book) uploadedBook;
        try {
            Book bookWithThisIsbn = bookManager.findBookByIsbn(upBook.getIsbn());
            if (bookWithThisIsbn != null) {
                errors.rejectValue("isbn", "uploadForm.selectFile", "This isbn is already exist!");
            }
        } catch (NoResultException ex) {
            //ignored
        }
        logger.debug("Validation has been completed.");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }
}
