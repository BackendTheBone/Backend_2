package com.nps.coco.domain.product.service;

import com.nps.coco.domain.product.dto.ProductInfo;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.seller.entity.Seller;
import com.nps.coco.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    /**
     * 상품 등록
     */
    public Long add(Long sellerId, ProductInfo info) {

        Seller seller = sellerRepository.findById(sellerId).orElseThrow();

        Product product = Product.builder()
                .seller(seller)
                .name(info.getName())
                .price(info.getPrice())
                .product_detail(info.getProduct_detail())
                .image(info.getImage())
                .build();

        productRepository.save(product);

        return product.getId();
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void edit(Long productId, ProductInfo info) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.update(info);
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.delete();
    }

}
