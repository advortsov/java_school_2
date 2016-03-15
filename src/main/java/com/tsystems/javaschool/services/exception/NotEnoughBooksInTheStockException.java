package com.tsystems.javaschool.services.exception;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public class NotEnoughBooksInTheStockException extends Exception{
    private int wantedQuantity;

    public NotEnoughBooksInTheStockException(int wantedQuantity) {
        this.wantedQuantity = wantedQuantity;
    }
}
