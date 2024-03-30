package io.nbc.selectedseat.web.excpetion;

import io.nbc.selectedseat.domain.member.exception.ExistFollowException;
import io.nbc.selectedseat.domain.member.exception.MisMatchPasswordException;
import io.nbc.selectedseat.domain.member.exception.NoSuchMemberException;
import io.nbc.selectedseat.domain.member.exception.NotEnoughCoinException;
import io.nbc.selectedseat.domain.member.exception.SamePasswordException;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<String>> handleException(
        RuntimeException ex
    ) {
        return ResponseEntity.badRequest()
            .body(
                ResponseDTO.<String>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .data(null)
                    .build()
            );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<String>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST,
            e.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(ExistFollowException.class)
    public ResponseEntity<ResponseDTO<String>> handleExistFollowException(
        ExistFollowException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST, e.getMessage()
        );
    }

    @ExceptionHandler(NoSuchMemberException.class)
    public ResponseEntity<ResponseDTO<String>> handleNoSuchMemberException(
        NoSuchMemberException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST, e.getMessage()
        );
    }

    @ExceptionHandler(MisMatchPasswordException.class)
    public ResponseEntity<ResponseDTO<String>> handleMisMatchPasswordException(
        MisMatchPasswordException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST, e.getMessage()
        );
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ResponseDTO<String>> handleSamePasswordException(
        SamePasswordException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST, e.getMessage()
        );
    }

    @ExceptionHandler(NotEnoughCoinException.class)
    public ResponseEntity<ResponseDTO<String>> handleNotEnoughCoinException(
        NotEnoughCoinException e
    ) {
        return createResponse(
            HttpStatus.BAD_REQUEST, e.getMessage()
        );
    }

    private ResponseEntity<ResponseDTO<String>> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status.value()).body(
            ResponseDTO.<String>builder()
                .statusCode(status.value())
                .message(message)
                .build()
        );
    }
}
