package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String role;
    private LocalDate createdAt;
    private String modifyBy;
    private LocalDate modifyAt;
}
