package com.nickross.auth.config;

import com.nickross.auth.domain.auth.models.account.exceptions.AccountNotFoundException;
import com.nickross.auth.domain.auth.models.account.exceptions.EmailAlreadyUsedException;
import com.nickross.auth.domain.auth.models.account.exceptions.InvalidPasswordException;
import com.nickross.auth.domain.auth.models.role.exceptions.RedundantRoleStateException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleInUseException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleNameAlreadyExistsException;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleNotFoundException;
import com.nickross.auth.dto.ApiResponse;
import com.nickross.auth.util.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  account exceptions
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleAccountNotFoundException(AccountNotFoundException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseUtil.failed(error));
    }
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseUtil.failed(error));
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleInvalidPasswordException(InvalidPasswordException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseUtil.failed(error));
    }

    //  role exceptions
    @ExceptionHandler(RedundantRoleStateException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleRedundantRoleStateException(RedundantRoleStateException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseUtil.failed(error));
    }
    @ExceptionHandler(RoleInUseException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleRoleInUseException(RoleInUseException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseUtil.failed(error));
    }
    @ExceptionHandler(RoleNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleRoleNameAlreadyExistsException(RoleNameAlreadyExistsException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseUtil.failed(error));
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void, Map<String,String>>> handleRoleNotFoundException(RoleNotFoundException e) {
        Map<String,String> error = Map.of("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseUtil.failed(error));
    }

    //  account_role exceptions

    //  general exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void, Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseUtil.failed("Error de validación", errors));
    }
}
