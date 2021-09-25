package com.ssafy.study.api.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    // 400
    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        log.warn("Runtime error", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("Access Denied error", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 401 - illegalArgument
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleIllegalArgumentException(final IllegalArgumentException ex) {
        log.warn("Illegal Argument", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

//    // 403 - JWT
//    @ExceptionHandler({  MalformedJwtException.class })
//    public ResponseEntity handleMalformedJwtException(final MalformedJwtException ex) {
//        log.warn("Invalid Token", ex);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//    }
//
//    // 403 - JWT
//    @ExceptionHandler({  ExpiredJwtException.class })
//    public ResponseEntity handleExpiredJwtException(final ExpiredJwtException ex) {
//        log.warn("Invalid Token", ex);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//    }
//
//    // 403 - JWT
//    @ExceptionHandler({UnsupportedJwtException.class})
//    public ResponseEntity handleUnsupportedJwtException(final UnsupportedJwtException ex) {
//        log.warn("Invalid Token", ex);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//    }

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("500 error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}