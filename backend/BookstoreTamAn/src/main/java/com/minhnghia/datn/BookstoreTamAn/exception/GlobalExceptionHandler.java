package com.minhnghia.datn.BookstoreTamAn.exception;

import com.minhnghia.datn.BookstoreTamAn.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> HandlingRuntimeException(RuntimeException ex) {
        ex.printStackTrace(); // Log lỗi để debug

        // Trả về ApiResponse với thông báo lỗi
        ApiResponse<String> response = ApiResponse.<String>builder()
                .code(500)
                .message(ex.getMessage())
                .errors(new HashMap<>()) // ✅ Đảm bảo errors không null
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> HandlingValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(exception.getDetailMessageCode());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.getOrDefault(MIN_ATTRIBUTE, "0"));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

}
