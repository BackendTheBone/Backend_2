package com.nps.coco.domain.product.controller;

import com.nps.coco.domain.product.dto.AddProductRequest;
import com.nps.coco.domain.product.dto.ProductInfo;
import com.nps.coco.domain.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final HttpSession session;

    public ResponseEntity<Long> addProduct(@RequestBody AddProductRequest request) {

        Long sellerId = (Long) session.getAttribute("sellerId");

        ProductInfo productInfo = ProductInfo.builder()
                .name(request.getName())
                .price(request.getPrice())
                .product_detail(request.getProduct_detail())
                .image(request.getImage())
                .build();

        Long productId = productService.add(sellerId, productInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

}
