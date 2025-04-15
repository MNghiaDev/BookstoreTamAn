package com.minhnghia.datn.BookstoreTamAn.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class BaseEntity {
    LocalDate createdAt;
    LocalDate modifyAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDate.now();
        modifyAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        modifyAt = LocalDate.now();
    }
}
