package com.renanresende.bridgetotalk.adapter.in.web.handler;

import com.renanresende.bridgetotalk.adapter.in.web.dto.ApiError;
import com.renanresende.bridgetotalk.adapter.in.web.resolver.ConstraintMessageResolver;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.exception.InvalidEnumValueException;
import com.renanresende.bridgetotalk.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ConstraintMessageResolver constraintMessageResolver;

    public GlobalExceptionHandler(ConstraintMessageResolver constraintMessageResolver) {
        this.constraintMessageResolver = constraintMessageResolver;
    }


    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ApiError> handleInvalidEnumValue(
            InvalidEnumValueException ex,
            HttpServletRequest request
    ) {

        var error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_PARAMETER",
                ex.getMessage() + " Allowed values: " + ex.getAllowedValues(),
                request.getRequestURI(),
                Instant.now()
        );

       return ResponseEntity.badRequest().body(error);
    }

    // 422 - Business Rules
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        var error = new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "BUSINESS_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.unprocessableEntity().body(error);
    }

    // 404 - Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        var error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 409 - Constraint violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        var dbMessage = ex.getMostSpecificCause().getMessage();
        var friendlyMessage = constraintMessageResolver.resolve(dbMessage);

        var error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                friendlyMessage,
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 500 - generic error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        var error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
