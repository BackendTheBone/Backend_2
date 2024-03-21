package com.nps.coco.domain.product.entity;

import com.nps.coco.domain.seller.entity.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "price", "product_detail", "image", "status"})
public class Product {

    @Id
    @GeneratedValue
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
    private String status;

    @Builder
    private Product(Seller seller, String name, Long price, String product_detail, String image) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.product_detail = product_detail;
        this.image = image;
        this.status = "N";
    }

}
