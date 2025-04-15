package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date orderDate;
    private String status;
    private Double totalPrice;
    private String email;
    private String paymentMethod;
    private String phone;
    private String shippingAddress;
    private String recipientName;
    private String createdBy;
    private String modifyBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
