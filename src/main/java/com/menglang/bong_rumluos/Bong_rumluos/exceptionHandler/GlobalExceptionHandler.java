package com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException e) {
        return BaseResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException e) {
        return BaseResponse.failed(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<BaseResponse> handleInternalServerException(InternalServerException e){
        return BaseResponse.failed(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<BaseResponse> handleConflictException(ConflictException e){
        return BaseResponse.failed(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseResponse> handleUnauthorizedException(UnauthorizedException ex){
        return BaseResponse.failed(ex.getMessage(),HttpStatus.UNAUTHORIZED);
    }


}
