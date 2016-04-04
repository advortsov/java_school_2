package com.tsystems.javaschool.dao.impl;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.dao.interfaces.ClientDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
@Repository
@Transactional
public class ClientDAOImpl extends AbstractJpaDAOImpl<Client> implements ClientDAO {

    private final static Logger logger = Logger.getLogger(ClientDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    public ClientDAOImpl() {
        super();
        setClazz(Client.class);
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        logger.debug("Sorting map by value...");
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        logger.debug("Map has been sorted.");
        return result;
    }

    public Client findByUserName(String name) throws NotRegisteredUserException {
        logger.debug("Finding client by user name...");
        Client client = null;
        String sql = "SELECT c FROM Client c WHERE c.user.userName = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        try {
            client = findOne(query);
        } catch (NoResultException ex) {
            throw new NotRegisteredUserException();
        }
        logger.debug("Returning client...");
        return client;
    }

    @Override
    public Map<Client, Integer> getTopTenClients() {
        logger.debug("Finding top ten clients...");
        Map<Client, Integer> topClients = new HashMap<>();

        String sql = "SELECT buy.client_id as clientId, SUM(order_line.book_price*order_line.quantity) as total " +
                "FROM buy JOIN order_line ON buy.id = order_line.order_id " +
                "JOIN book ON order_line.book_id = book.id " +
                "GROUP BY clientId ORDER BY total DESC LIMIT 10";

        List<Object[]> resultList = em.createNativeQuery(sql).getResultList();

        for (Object[] result : resultList) {
            BigInteger clientId = (BigInteger) result[0];
            long id = clientId.longValue();
            BigDecimal clientSumm = (BigDecimal) result[1];
            int summ = clientSumm.intValue();
            topClients.put(this.findByID(Client.class, id), summ);
        }
        logger.debug("Returning top ten clients...");
        return sortByValue(topClients);
    }

    @Override
    public UserRole getUserRoleByName(String name) {
        logger.debug("Searching one of user roles...");
        UserRole userRole = null;
        String sql = "SELECT u FROM UserRole u WHERE u.userRole = :name";
        Query query = em.createQuery(sql).
                setParameter("name", name);
        userRole = (UserRole) query.getSingleResult();
        logger.debug("Returning found user role.");
        return userRole;
    }
}
