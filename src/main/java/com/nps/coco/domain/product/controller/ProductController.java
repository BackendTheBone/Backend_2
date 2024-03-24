package com.nps.coco.domain.product.controller;

import com.nps.coco.domain.product.dto.AddProductRequest;
import com.nps.coco.domain.product.dto.ProductInfo;
import com.nps.coco.domain.product.dto.UpdateProductRequest;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final HttpSession session;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest request) {

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

    @PostMapping("/products/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable("productId") Long productId,
                                         @RequestBody UpdateProductRequest request) {

        ProductInfo productInfo = ProductInfo.builder()
                .name(request.getName())
                .price(request.getPrice())
                .product_detail(request.getProduct_detail())
                .image(request.getImage())
                .build();

        productService.edit(productId, productInfo);
        return ResponseEntity.ok(productId);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(productId);
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
