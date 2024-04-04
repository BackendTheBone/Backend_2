package com.nps.coco.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateProductForm {

    @NotNull
    private String name;

    @NotNull
    private Long price;

    private String product_detail;

    private MultipartFile image;

}
