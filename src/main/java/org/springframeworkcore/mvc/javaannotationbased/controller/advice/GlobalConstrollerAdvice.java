package org.springframeworkcore.mvc.javaannotationbased.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalConstrollerAdvice {

	  @ExceptionHandler(Exception.class)
	    public String handleException(Exception e, Model model) {
	        model.addAttribute("errorMessage", e.getMessage());
	        return "exception"; // JSP name
	    }
	
}
