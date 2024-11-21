package com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.ConflictException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.InternalServerException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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


}
