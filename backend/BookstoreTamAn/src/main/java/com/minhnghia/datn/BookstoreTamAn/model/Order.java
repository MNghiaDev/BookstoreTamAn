package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;
    private LocalDate orderDate;
    private String status;
    private Double totalPrice;
    private String email;
    private String paymentMethod;
    private String phone;
    private String shippingAddress;
    private String recipientName;
    private boolean active;
    private String createdBy;
    private String modifyBy;
    private Boolean paid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
}
