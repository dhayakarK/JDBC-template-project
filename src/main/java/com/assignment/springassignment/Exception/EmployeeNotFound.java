package com.assignment.springassignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


public class EmployeeNotFound extends RuntimeException {

    public EmployeeNotFound(String message)
    {
        super(message);
    }
   public EmployeeNotFound(String message,Throwable cause)
   {
       super(message,cause);
   }
   public EmployeeNotFound(Throwable cause)
   {
       super(cause);
   }

}
