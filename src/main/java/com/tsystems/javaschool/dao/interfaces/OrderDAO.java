package com.tsystems.javaschool.dao.interfaces;


import com.tsystems.javaschool.dao.entity.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */
public interface OrderDAO extends AbstractJpaDAO<Order> {
    //admin features:

        // ???? ???
    void saveOrder(Order order);

    void deductBooksFromStore(Order order);

    public List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd);

}
