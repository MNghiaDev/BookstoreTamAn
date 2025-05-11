package com.minhnghia.datn.BookstoreTamAn.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationResponse {
    Integer orderId;
    Double totalPrice;
    String status;
}
