package com.nps.coco.domain.product.entity;

import com.nps.coco.domain.seller.entity.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @NotNull
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private String product_detail;

    @NotNull
    private String image;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String status;

    @Builder
    private Product(Seller seller, String name, Long price, String product_detail, String image) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.product_detail = product_detail;
        this.image = image;
    }

}
