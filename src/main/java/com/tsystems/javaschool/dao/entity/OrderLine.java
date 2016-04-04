package com.tsystems.javaschool.dao.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */

@Entity
@Table(name = "order_line")//
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "book_id", nullable = true)
    private Book book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "book_isbn", nullable = false)
    private String bookIsbn; // чтобы при удалении книги, или изменении ее цены, остались данные

    @Column(name = "book_price", nullable = false)
    private int bookActualPrice; // чтобы при удалении книги, или изменении ее цены, остались данные

    public OrderLine() {

    }

    public OrderLine(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getBookActualPrice() {
        return bookActualPrice;
    }

    public void setBookActualPrice(int bookActualPrice) {
        this.bookActualPrice = bookActualPrice;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;

        // заполняем неизменяемые поля в процессе создания ордерлайна
        this.setBookIsbn(book.getIsbn());
        this.setBookActualPrice(book.getPrice());
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (id != orderLine.id) return false;
        if (quantity != orderLine.quantity) return false;
        if (!order.equals(orderLine.order)) return false;
        return book.equals(orderLine.book);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + order.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + book.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", order.id=" + order.getId() +
                ", book=" + book +
                ", quantity=" + quantity +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookActualPrice=" + bookActualPrice +
                '}';
    }
}
