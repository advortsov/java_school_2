package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */

@Repository
@Transactional
public class OrderDAOImpl extends AbstractJpaDAOImpl<Order> implements OrderDAO {

    private final static Logger logger = Logger.getLogger(BookDAOImpl.class);

    @Autowired
    private BookDAO bookDAO;

    @PersistenceContext
    private EntityManager em;

    public OrderDAOImpl() {
        super();
        setClazz(Order.class);
    }

    @Override
    public void saveOrder(Order order) {
        logger.debug("Saving new order...");
        deductBooksFromStore(order);
        this.save(order);
        logger.debug("Order has been saved.");
    }

    @Override
    public void deductBooksFromStore(Order order) {
        logger.debug("Deducting books from store...");
        List<OrderLine> orderLines = order.getOrderLines();
        for (OrderLine orderLine : orderLines) {
            long bookId = orderLine.getBook().getId();
            int quantity = orderLine.getQuantity();
            bookDAO.setBookQuantity(bookId, quantity);
        }
        logger.debug("All books were successfully deducted.");
    }

    public List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd) {
        logger.debug("Getting orders per period...");
        List<Order> orders = null;
        String sql = "SELECT o FROM Order o WHERE o.date >= :periodStart AND o.date <= :periodEnd";
        Query query = em.createQuery(sql).
                setParameter("periodStart", periodStart).setParameter("periodEnd", periodEnd);
        orders = this.findMany(query);
        logger.debug("Return orders per period.");
        return orders;
    }
}
