package com.minhnghia.datn.BookstoreTamAn.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

    private int cartItemId;
    private int quantity;
}
