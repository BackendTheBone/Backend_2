package com.nps.coco.domain.pay.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Column(nullable = false)
    private String pay_method;

    @Column(nullable = false)
    private Integer pay_number;

    @Column(nullable = false)
    private Long total_price;

    @Builder
    public Pay(String pay_method, Integer pay_number, Long total_price) {
        this.pay_method = pay_method;
        this.pay_number = pay_number;
        this.total_price = total_price;
    }

}
