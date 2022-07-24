package com.tweetbox;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionFilter {
    @SneakyThrows
    @ExceptionHandler(value = Exception.class)
    public Exception ExceptionHandler(Exception e, HttpServletRequest request) {

        if (e instanceof ResponseStatusException) {
            
            throw e;
        } else {
            throw e;
        }
    }
}
