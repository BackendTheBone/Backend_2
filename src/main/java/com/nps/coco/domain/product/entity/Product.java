package com.nps.coco.domain.product.entity;

import com.nps.coco.domain.product.dto.UpdateProductRequest;
import com.nps.coco.domain.seller.entity.Seller;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String product_detail;

    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Builder
    private Product(Seller seller, String name, Long price, String product_detail, String image) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.product_detail = product_detail;
        this.image = image;
        this.status = ProductStatus.ACTIVE;
    }

    //==비즈니스 로직==//

    /**
     * 상품 수정
     */
    public void update(UpdateProductRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.product_detail = request.getProduct_detail();
        this.image = request.getImage();
    }

    /**
     * 상품 삭제
     */
    public void delete() {
        this.status = ProductStatus.INACTIVE;
    }

}
