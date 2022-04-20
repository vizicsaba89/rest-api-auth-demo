package hu.vizicsaba.restapidemo.web.controller;

import hu.vizicsaba.restapidemo.service.exception.RestApiDemoNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
        Map<String, String> response = new HashMap<String, String>() {{
            put("error", ex.getMessage());
        }};

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { RestApiDemoNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
        Map<String, String> response = new HashMap<String, String>() {{
            put("error", "entity not found");
        }};

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
