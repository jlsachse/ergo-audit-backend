package com.ergoton.ergoaudit.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Problem> handleEntityAlreadyExists(EntityAlreadyExistsException exception,
                                                             HttpServletRequest httpRequest) throws URISyntaxException {

        return create(
                Problem.builder()
                        .withTitle("Entity already exists")
                        .withDetail(exception.getMessage())
                        .withInstance(new URI(httpRequest.getRequestURI()))
                        .withStatus(Status.CONFLICT)
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleEntityNotFound(EntityNotFoundException exception,
                                                        HttpServletRequest httpRequest) throws URISyntaxException {
        return create(
                Problem.builder()
                        .withTitle("Can't find entity")
                        .withDetail(exception.getMessage())
                        .withInstance(new URI(httpRequest.getRequestURI()))
                        .withStatus(Status.NOT_FOUND)
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Problem> handleBadCredentials(BadCredentialsException exception,
                                                        HttpServletRequest httpRequest) throws URISyntaxException {
        return create(
                Problem.builder()
                        .withTitle("Couldn't authenticate wallet")
                        .withDetail(exception.getMessage())
                        .withInstance(new URI(httpRequest.getRequestURI()))
                        .withStatus(Status.NOT_FOUND)
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }



    private ResponseEntity<Problem> create(Problem problem, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(problem);
    }

}
