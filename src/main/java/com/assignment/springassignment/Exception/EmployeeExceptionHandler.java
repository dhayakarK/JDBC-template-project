package com.assignment.springassignment.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<Object> handleException1(EmployeeNotFound employeeNotFound)
    {
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError(employeeNotFound.getMessage());
        return new ResponseEntity<Object>(errorResponse,HttpStatus.CONFLICT );
    }
    @ExceptionHandler()
    public ResponseEntity<Object> handleException2(EmployeeDelNotFound delNotFound)
    {
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(delNotFound.getMessage());
        return new ResponseEntity<Object>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponse response=new ErrorResponse(400,ex.getBindingResult().toString());
        response.setError("please enter proper email");
        return new ResponseEntity<Object>(response,HttpStatus.NO_CONTENT);
    }

}
