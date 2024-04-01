package com.nps.coco.domain.product.controller;

import com.nps.coco.domain.product.dto.CreateProductRequest;
import com.nps.coco.domain.product.dto.UpdateProductRequest;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.product.service.ProductService;
import com.nps.coco.domain.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/products/add")
    public ResponseEntity<?> add(@SessionAttribute(name = "seller", required = false) Seller seller,
                                 @RequestBody CreateProductRequest request) {

        if (seller == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        productService.add(seller, request);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/products/{productId}/edit")
    public ResponseEntity<?> edit(@SessionAttribute(name = "seller", required = false) Seller seller,
                                  @PathVariable("productId") Long productId,
                                  @RequestBody UpdateProductRequest request) {

        if (seller == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        productService.edit(seller, productId, request);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/products/{productId}/delete")
    public ResponseEntity<?> delete(@SessionAttribute(name = "seller", required = false) Seller seller,
                                    @PathVariable("productId") Long productId) {

        if (seller == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        productService.delete(seller, productId);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> productDetails(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productRepository.findById(productId));
    }

    @GetMapping("/products")
    public Page<Product> productList(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
