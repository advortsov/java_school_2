package ru.javabegin.training.objects;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.enums.PaymentStatus;
import com.tsystems.javaschool.services.enums.PaymentType;
import com.tsystems.javaschool.services.enums.ShippingType;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 23.03.2016
 */
public class CreatedOrder {

//    private Client client;
//    private OrderStatus orderStatus;
//    private PaymentStatus paymentStatus;
//    private PaymentType paymentType;
//    private ShippingType shippingType;
//    private Date date;
//    private List<OrderLine> orderLines;

    private String book_count;

    public String getBook_count() {
        return book_count;
    }

    public void setBook_count(String book_count) {
        this.book_count = book_count;
    }
}
