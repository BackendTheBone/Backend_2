package com.nps.coco.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
    private String name;
    private Long price;
    private String product_detail;
    private String image;
}
