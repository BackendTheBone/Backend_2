package com.nps.coco.domain.product.service;

import com.nps.coco.domain.product.dto.CreateProductRequest;
import com.nps.coco.domain.product.dto.UpdateProductRequest;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.entity.ProductStatus;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.seller.entity.Seller;
import com.nps.coco.domain.seller.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void 상품등록() {
        //given
        Seller seller = seller1();

        CreateProductRequest request = CreateProductRequest.builder()
                .name("name1")
                .price(1L)
                .product_detail("product_detail1")
                .image("image1")
                .build();

        //when
        Long productId = productService.add(seller, request);

        //then
        Product getProduct = productRepository.findById(productId).orElseThrow();

        assertEquals(seller.getId(), getProduct.getSeller().getId());
        assertEquals("name1", getProduct.getName());
        assertEquals(1L, getProduct.getPrice());
        assertEquals("product_detail1", getProduct.getProduct_detail());
        assertEquals("image1", getProduct.getImage());
        assertEquals(ProductStatus.ACTIVE, getProduct.getStatus());
    }

    @Test
    void 상품수정() {
        //given
        Seller seller = seller1();
        Product product = product1(seller);

        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("name2")
                .price(2L)
                .product_detail("product_detail2")
                .image("image2")
                .build();

        //when
        Long productId = productService.edit(seller, product.getId(), request);

        //then
        Product getProduct = productRepository.findById(product.getId()).orElseThrow();

        assertEquals(productId, getProduct.getId());
        assertEquals("name2", getProduct.getName());
        assertEquals(2L, getProduct.getPrice());
        assertEquals("product_detail2", getProduct.getProduct_detail());
        assertEquals("image2", getProduct.getImage());
    }

    @Test
    void 상품수정_판매자불일치() {
        //given
        Seller seller1 = seller1();
        Seller seller2 = seller2();
        Product product = product1(seller1);

        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("name2")
                .price(2L)
                .product_detail("product_detail2")
                .image("image2")
                .build();

        //when
        assertThrows(IllegalStateException.class, () -> productService.edit(seller2, product.getId(), request));
    }

    Seller seller1() {
        Seller seller = Seller.builder()
                .email("email1")
                .password("password1")
                .name("name1")
                .build();

        sellerRepository.save(seller);

        return seller;
    }

    Seller seller2() {
        Seller seller = Seller.builder()
                .email("email2")
                .password("password2")
                .name("name2")
                .build();

        sellerRepository.save(seller);

        return seller;
    }

    Product product1(Seller seller) {
        Product product = Product.builder()
                .seller(seller)
                .name("name1")
                .price(1L)
                .product_detail("product_detail1")
                .image("image1")
                .build();

        productRepository.save(product);

        return product;
    }

    Product product2(Seller seller) {
        Product product = Product.builder()
                .seller(seller)
                .name("name2")
                .price(2L)
                .product_detail("product_detail2")
                .image("image2")
                .build();

        productRepository.save(product);

        return product;
    }

}