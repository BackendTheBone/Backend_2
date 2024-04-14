package com.nps.coco.domain.basket.repository;

import com.nps.coco.domain.basket.entity.Basket;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findBasketsByUser(User user);
    Optional<Basket> findBasketByUserAndProduct(User user, Product product);

    // Basket 테이블에서 특정 사용자에 속하는 특정 ID 리스트에 해당하는 레코드만 삭제
    @Modifying // MDL 쿼리가 발생할 때 적용해주는 어노테이션
    @Query("delete from Basket b where b.id in :ids and b.user.userId = :userId")
    void deleteAllByIds(@Param("ids") List<Long> ids, @Param("userId") Long userId);

    Long countByUser(User user);
}
