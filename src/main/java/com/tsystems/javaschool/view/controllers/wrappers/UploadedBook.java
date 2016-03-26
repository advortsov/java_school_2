package com.tsystems.javaschool.view.controllers.wrappers;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 22.03.2016
 */
public class UploadedBook {

    private String book_name;
    private String book_isbn;
    private int book_pages;
    private int book_year;
    private MultipartFile book_cover;
    private String book_author;
    private String book_genre;
    private String book_publisher;
    private int book_count;
    private int book_price;

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public int getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(int book_pages) {
        this.book_pages = book_pages;
    }

    public int getBook_year() {
        return book_year;
    }

    public void setBook_year(int book_year) {
        this.book_year = book_year;
    }

    public MultipartFile getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(MultipartFile book_cover) {
        this.book_cover = book_cover;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_genre() {
        return book_genre;
    }

    public void setBook_genre(String book_genre) {
        this.book_genre = book_genre;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public int getBook_count() {
        return book_count;
    }

    public void setBook_count(int book_count) {
        this.book_count = book_count;
    }

    public int getBook_price() {
        return book_price;
    }

    public void setBook_price(int book_price) {
        this.book_price = book_price;
    }
}
