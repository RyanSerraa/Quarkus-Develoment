package org.acme;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;

public class ResponseError {
    private String message;
    private Collection<FieldError> errors;
    
    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>>violations){
      List <FieldError> errors =  violations.stream().map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage())).toList();
      String message ="Validation Error";
      var responseError= new ResponseError(message,errors);
      return  responseError;

    }

    public ResponseError(String message, Collection<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Collection<FieldError> getErrors() {
        return errors;
    }
    public void setErrors(Collection<FieldError> errors) {
        this.errors = errors;
    } 
    
}
