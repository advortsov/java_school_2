package com.tsystems.javaschool.services.exception;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public class NotEnoughBooksInTheStockException extends Exception {
    private int wantedQuantity;

    public NotEnoughBooksInTheStockException(String message, int wantedQuantity) {
        super(message);
        this.wantedQuantity = wantedQuantity;
    }


}
