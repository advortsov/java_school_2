package com.tsystems.javaschool.view.controllers.validators;

import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.interfaces.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 23.03.2016
 */
@Component
public class OrderValidator implements Validator {

    @Autowired
    private BookManager bookManager;

    @Override
    public void validate(Object createdOrder, Errors errors) {

        Order crOrder = (Order) createdOrder;

        List<OrderLine> lines = crOrder.getOrderLines();

        for (OrderLine line : lines) {
            if (line.getQuantity() > bookManager.getBookQuantity(line.getBook().getId())) {
                errors.rejectValue("orderLines", "uploadForm.selectFile",
                        "Not enough books '" + line.getBook().getName() + "' in stock. Please, choose less number.");
            }
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }
}
