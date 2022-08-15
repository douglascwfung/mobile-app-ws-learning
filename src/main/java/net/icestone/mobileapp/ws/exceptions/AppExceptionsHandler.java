package net.icestone.mobileapp.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.icestone.mobileapp.ws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
	
	
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
//        
//    	String errorMessageDescription = ex.getLocalizedMessage();
//    	
//    	if(errorMessageDescription == null) errorMessageDescription = ex.toString();
//    	
//    	ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
//    	
//    	return new ResponseEntity<>(
//    			errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    	
//    }
	
    @ExceptionHandler(value = { UserServiceException.class})
    public ResponseEntity<Object> handleSpecificExceptions(UserServiceException ex, WebRequest request) 
    {
    	ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

    	return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) 
    {
    	ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

    	return new ResponseEntity<>( errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

