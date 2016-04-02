package com.tsystems.javaschool.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 * <p>
 * Pojo
 */

@Entity
@Table(name = "book")
@NamedQuery(name = "Book.getAll", query = "SELECT b from Book b")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "page_count")
    private int pageCount;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "publish_year")
    private int publishYear;

    @Column(name = "image", length = 1048576) // 1 mb
    @Lob()
    private byte[] image;

    @Column(name = "descr")
    private String descr;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    public Book() {
    }

    public Book(String name, int pageCount, String isbn, int publishYear, byte[] image,
                String descr, Author author, Genre genre, Publisher publisher, int quantity, int price) {
        this.name = name;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.image = image;
        this.descr = descr;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.quantity = quantity;
        this.price = price;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (pageCount != book.pageCount) return false;
        if (publishYear != book.publishYear) return false;
        if (price != book.price) return false;
        if (!name.equals(book.name)) return false;
        if (!isbn.equals(book.isbn)) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) return false;
        return !(publisher != null ? !publisher.equals(book.publisher) : book.publisher != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + pageCount;
        result = 31 * result + isbn.hashCode();
        result = 31 * result + publishYear;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        String  img = "nulll";
        if(image != null) img = String.valueOf(image.length);

        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pageCount=" + pageCount +
                ", isbn='" + isbn + '\'' +
                ", publishYear=" + publishYear +
                ", image length=" + img +
                ", descr='" + descr + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", publisher=" + publisher +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
