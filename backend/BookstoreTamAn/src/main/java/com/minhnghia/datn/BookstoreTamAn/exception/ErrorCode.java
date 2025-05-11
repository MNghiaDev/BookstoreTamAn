package com.minhnghia.datn.BookstoreTamAn.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1009, "Email must be valid", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(10010, "Email existed", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(1011, "Book not found!", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1012, "Username existed", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1013, "Phone existed", HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_FOUND(1014, "Author not found!", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1015, "Category not found!", HttpStatus.NOT_FOUND),
    NEWS_NOT_FOUND(1016, "News not found!", HttpStatus.NOT_FOUND),
    ERROR_USERNAME_OR_PASSWORD(1017, "Sai tên đăng nhập hoặc mật khẩu", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1018, "Order not found!", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
