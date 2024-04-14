package com.nps.coco.domain.basket.entity;

import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity //엔티티 지정 어노테이션
@Getter
// 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
// private로 하지 않는 이유 : 엔티티의 프록시 조회 때문!
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id
    @GeneratedValue
    @Column(name="basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @CreatedDate // 엔티티 생성될때 날짜+시간 자동
    @Column(name = "created_at", updatable = false,nullable = false) // 업데이트 안되도록 막기
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티 업데이트될때 날짜+시간 자동
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Basket(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}
