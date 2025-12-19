package com.renanresende.bridgetotalk.adapter.in.web.handler;

import com.renanresende.bridgetotalk.adapter.in.web.dto.ApiError;
import com.renanresende.bridgetotalk.adapter.in.web.resolver.ConstraintMessageResolver;
import com.renanresende.bridgetotalk.domain.shared.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.shared.exception.ResourceAlreadyExistsException;
import com.renanresende.bridgetotalk.domain.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.Instant;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ConstraintMessageResolver constraintMessageResolver;

    public GlobalExceptionHandler(ConstraintMessageResolver constraintMessageResolver) {
        this.constraintMessageResolver = constraintMessageResolver;
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
    @ExceptionHandler({DataIntegrityViolationException.class,
    })
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

    // Específico para o erro de negócio de duplicidade (409)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExists(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        var error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage(),
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
               "An unexpected error occurred",
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {

            if (invalidFormatException.getTargetType().isEnum()) {
                var field = invalidFormatException.getPath().get(invalidFormatException.getPath().size() - 1).getPropertyName();
                var invalidValue = invalidFormatException.getValue().toString();
                var validValues = Arrays.toString(invalidFormatException.getTargetType().getEnumConstants());

                var message = String.format(
                        "Valor inválido '%s' para o campo '%s'. Valores permitidos: %s",
                        invalidValue, field, validValues
                );

                var errors =  new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        "INVALID_PARAMETER",
                       message,
                        request.getRequestURI(),
                        Instant.now()
                );

                return ResponseEntity.badRequest().body(errors);
            }
        }

        var errors =  new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "REQUEST_MALFORMED",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(
            HandlerMethodValidationException ex,
            HttpServletRequest request
            ) {

        var errorMessage = "";
        var erro = ex.getAllErrors().stream().findFirst();

        if (erro.isPresent()) {
           errorMessage = erro.get().getDefaultMessage();
        }else {
            errorMessage = ex.getMessage();
        }

        var apiError =  new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "REQUEST_MALFORMED",
                errorMessage,
                request.getRequestURI(),
                Instant.now()
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}
