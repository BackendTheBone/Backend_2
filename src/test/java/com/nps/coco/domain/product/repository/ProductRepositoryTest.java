package com.nps.coco.domain.product.repository;

import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.seller.entity.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testProduct() {
        Seller seller = Seller.builder().email("email").password("password").name("name").build();
        Product product = Product.builder().seller(seller).name("name").price(1L).product_detail("product_detail").image("image").build();
        Product savedProduct = productRepository.save(product);

        Product findProduct = productRepository.findById(savedProduct.getId()).orElseThrow();

        assertThat(findProduct.getId()).isEqualTo(product.getId());
        assertThat(findProduct.getSeller()).isEqualTo(product.getSeller());
        assertThat(findProduct.getName()).isEqualTo(product.getName());
        assertThat(findProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(findProduct.getProduct_detail()).isEqualTo(product.getProduct_detail());
        assertThat(findProduct.getImage()).isEqualTo(product.getImage());
        assertThat(findProduct.getStatus()).isEqualTo(product.getStatus());
        assertThat(findProduct).isEqualTo(product);
    }

    @Test
    public void basicCRUD() {
        Seller seller = Seller.builder().email("email").password("password").name("name").build();
        Product product1 = Product.builder().seller(seller).name("name1").price(1L).product_detail("product_detail1").image("image1").build();
        Product product2 = Product.builder().seller(seller).name("name2").price(2L).product_detail("product_detail2").image("image2").build();
        productRepository.save(product1);
        productRepository.save(product2);

        Product findProduct1 = productRepository.findById(product1.getId()).orElseThrow();
        Product findProduct2 = productRepository.findById(product2.getId()).orElseThrow();
        assertThat(findProduct1).isEqualTo(product1);
        assertThat(findProduct2).isEqualTo(product2);

        List<Product> all = productRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = productRepository.count();
        assertThat(count).isEqualTo(2);

        productRepository.delete(product1);
        productRepository.delete(product2);

        long deletedCount = productRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

}
