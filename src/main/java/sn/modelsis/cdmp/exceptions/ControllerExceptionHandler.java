package sn.modelsis.cdmp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sn.modelsis.cdmp.exceptions.message.ErrorMessage;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(CustomException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                503,
                new Date(),
                ex.getMessage(),
                "Exception occured why handling by the server");

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}