package com.nps.coco.domain.product.service;

import com.nps.coco.domain.product.dto.CreateProductRequest;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.seller.entity.Seller;
import com.nps.coco.domain.seller.repository.SellerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Test
    void add() {
        //given
        Seller seller = Seller
                .builder()
                .email("email")
                .password("password")
                .name("name")
                .build();

        sellerRepository.save(seller);

        CreateProductRequest request = CreateProductRequest
                .builder()
                .name("name")
                .price(0L)
                .product_detail("product_detail")
                .image("image")
                .build();

        //when
        Long savedId = productService.add(seller, request);

        //then
        Product product = productRepository.findById(savedId).orElseThrow();
        Assertions.assertEquals(seller, product.getSeller());
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}