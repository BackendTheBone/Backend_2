package com.nps.coco.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateProductRequest {

    @NotNull
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private String product_detail;

    @NotNull
    private String image;

}
