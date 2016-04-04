package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.exception.EmptyOrderException;
import com.tsystems.javaschool.services.exception.NotEnoughBooksInTheStockException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateException.class)
    public ModelAndView handleDuplicateException(DuplicateException ex) {
        logger.error("DuplicateException handler executed:");
        logger.error(String.valueOf(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
        return modelAndView;
    }

    @ExceptionHandler(EmptyOrderException.class)
    public ModelAndView handleEmptyOrderException(EmptyOrderException ex) {
        logger.error("EmptyOrderException handler executed:");
        logger.error(String.valueOf(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
        return modelAndView;
    }

    @ExceptionHandler(NotEnoughBooksInTheStockException.class)
    public ModelAndView handleNotEnoughBooksInTheStockException(NotEnoughBooksInTheStockException ex) {
        logger.error("NotEnoughBooksInTheStockException handler executed:");
        logger.error(String.valueOf(ex.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        logger.error("Exception handler executed:");
        logger.error(ex);
        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("stacktrace", Arrays.toString(ex.getStackTrace()));
        return modelAndView;
    }

}
