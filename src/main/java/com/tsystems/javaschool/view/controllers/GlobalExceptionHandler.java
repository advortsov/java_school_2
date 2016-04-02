package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.services.exception.DuplicateException;
import com.tsystems.javaschool.services.exception.EmptyOrderException;
import com.tsystems.javaschool.services.exception.NotEnoughBooksInTheStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
//    @ExceptionHandler(IOException.class)
//    public void handleIOException() {
//        logger.error("IOException handler executed");
//    }


//    @ExceptionHandler(DuplicateException.class)
//    public ModelAndView handleDuplicateException(DuplicateException ex) {
//        System.out.println("GLOBAL HANDLER DuplicateException = " + ex.getClass());
//        logger.error("DuplicateException handler executed:");
//        logger.error(String.valueOf(ex.getStackTrace()));
//        ModelAndView modelAndView = new ModelAndView("error.jsp");
//        modelAndView.addObject("error", ex.getMessage());
//        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
//        return modelAndView;
//    }
//
//    @ExceptionHandler(EmptyOrderException.class)
//    public ModelAndView handleEmptyOrderException(EmptyOrderException ex) {
//        System.out.println("GLOBAL HANDLER EmptyOrderException = " + ex.getClass());
//
//        logger.error("EmptyOrderException handler executed:");
//        logger.error(String.valueOf(ex.getStackTrace()));
//        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
//        modelAndView.addObject("error", ex.getMessage());
//        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
//        return modelAndView;
//    }
//
//    @ExceptionHandler(NotEnoughBooksInTheStockException.class)
//    public ModelAndView handleNotEnoughBooksInTheStockException(NotEnoughBooksInTheStockException ex) {
//        System.out.println("GLOBAL HANDLER handleNotEnoughBooksInTheStockException = " + ex.getClass());
//
//        System.out.println("ex.getMessage() = " + ex.getMessage());
//        logger.error("NotEnoughBooksInTheStockException handler executed:");
//        logger.error(String.valueOf(ex.getStackTrace()));
//        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
//        modelAndView.addObject("error", ex.getMessage());
//        modelAndView.addObject("stacktrace", String.valueOf(ex.getStackTrace()));
//        return modelAndView;
//    }
//
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception ex) {
//        System.out.println("GLOBAL HANDLER Exception = " + ex.getClass());
//        logger.error("Exception handler executed:");
//        logger.error(String.valueOf(ex.getStackTrace()));
//        ModelAndView modelAndView = new ModelAndView("/error/error.jsp");
//        modelAndView.addObject("error", ex.getMessage());
//        modelAndView.addObject("stacktrace", Arrays.toString(ex.getStackTrace()) );
//        return modelAndView;
//    }
}
