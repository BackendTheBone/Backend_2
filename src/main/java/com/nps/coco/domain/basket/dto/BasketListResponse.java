package com.nps.coco.domain.basket.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasketListResponse {

    @NotNull
    private Long basketId;

    @NotNull
    private Long productId;

    @NotNull
    private String sellerName;

    @NotNull
    private String productName;

    @NotNull
    private Long productPrice;

    private String productDetail;

    private String image;

    @Builder
    public BasketListResponse(Long basketId, Long productId, String sellerName, String productName, Long productPrice, String productDetail, String image) {
        this.basketId = basketId;
        this.productId = productId;
        this.sellerName = sellerName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.image = image;
    }
}
