package com.nps.coco.domain.product.dto;

import lombok.Getter;

@Getter
public class UpdateProductRequest {
    private String name;
    private Long price;
    private String product_detail;
    private String image;
}
