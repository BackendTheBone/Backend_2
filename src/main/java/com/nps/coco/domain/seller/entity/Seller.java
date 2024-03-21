package com.nps.coco.domain.seller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "email", "password", "name", "status"})
public class Seller {

    @Id
    @GeneratedValue
    @Column(name = "seller_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String status;

    @Builder
    private Seller(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.status = "N";
    }

}
