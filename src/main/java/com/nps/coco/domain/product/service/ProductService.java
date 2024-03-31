package com.nps.coco.domain.product.service;

import com.nps.coco.domain.product.dto.CreateProductRequest;
import com.nps.coco.domain.product.dto.UpdateProductRequest;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long add(Seller seller, CreateProductRequest request) {

        Product product =
                Product.builder()
                        .seller(seller)
                        .name(request.getName())
                        .price(request.getPrice())
                        .product_detail(request.getProduct_detail())
                        .image(request.getImage()).build();

        productRepository.save(product);
        return product.getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void edit(Seller seller, Long productId, UpdateProductRequest request) {

        Product product = productRepository.findById(productId).orElseThrow();

        validateLoginSeller(seller, product);

        product.update(request);
        productRepository.save(product);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void delete(Seller seller, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow();

        validateLoginSeller(seller, product);

        product.delete();
        productRepository.save(product);
    }

    private void validateLoginSeller(Seller seller, Product product) {
        if (seller != product.getSeller()) {
            throw new IllegalStateException("");
        }
    }

}
