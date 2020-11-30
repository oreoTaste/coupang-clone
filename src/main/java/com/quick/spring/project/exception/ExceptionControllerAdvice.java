package com.quick.spring.project.exception;

import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

    @ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler()
    public ModelAndView error(Exception exception, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        LogManager.getLogger(req.getServletPath())
                  .info(exception.getMessage() + " :" + exception.getClass().getSimpleName());
        return mav;
    }
}
