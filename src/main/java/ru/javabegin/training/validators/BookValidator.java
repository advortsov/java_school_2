package ru.javabegin.training.validators;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javabegin.training.objects.UploadedBook;

import javax.persistence.NoResultException;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 22.03.2016
 */

@Component
public class BookValidator implements Validator {

    @Autowired
    private BookManager bookManager;

    @Override
    public void validate(Object uploadedBook, Errors errors) {

        UploadedBook upBook = (UploadedBook) uploadedBook;
        Book bookWithThisIsbn = bookManager.findBookByIsbn(upBook.getBook_isbn());
        try {
            if (bookWithThisIsbn != null) {
                errors.rejectValue("book_isbn", "uploadForm.selectFile", "This isbn is already exist!");
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
