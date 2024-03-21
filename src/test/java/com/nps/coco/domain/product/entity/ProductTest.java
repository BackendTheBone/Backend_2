package com.nps.coco.domain.product.entity;

import com.nps.coco.domain.seller.entity.Seller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ProductTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testEntity() {

        Seller sellerA = Seller.builder().email("emailA").password("passwordA").name("nameA").build();
        Seller sellerB = Seller.builder().email("emailB").password("passwordB").name("nameB").build();

        em.persist(sellerA);
        em.persist(sellerB);

        Product product1 = Product.builder().seller(sellerA).name("name1").price(1L).product_detail("product_detail1").image("image1").build();
        Product product2 = Product.builder().seller(sellerA).name("name2").price(2L).product_detail("product_detail2").image("image2").build();
        Product product3 = Product.builder().seller(sellerB).name("name3").price(3L).product_detail("product_detail3").image("image3").build();
        Product product4 = Product.builder().seller(sellerB).name("name4").price(4L).product_detail("product_detail4").image("image4").build();

        em.persist(product1);
        em.persist(product2);
        em.persist(product3);
        em.persist(product4);

        em.flush();
        em.clear();

        List<Product> products = em.createQuery("select p from Product p", Product.class).getResultList();
        for (Product product : products) {
            System.out.println("product=" + product);
            System.out.println("-> product.seller=" + product.getSeller());
        }
    }

}
