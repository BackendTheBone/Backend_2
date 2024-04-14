package com.nps.coco.domain.basket.controller;

import com.nps.coco.domain.basket.dto.BasketListResponse;
import com.nps.coco.domain.basket.dto.CreateBasketRequest;
import com.nps.coco.domain.basket.dto.DeleteBasketRequest;
import com.nps.coco.domain.basket.entity.Basket;
import com.nps.coco.domain.basket.service.BasketService;
import com.nps.coco.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    /**
     * 장바구니 등록
     */
    @PostMapping("/baskets/add")
    public ResponseEntity<?> add(@SessionAttribute(name = "user", required = false) User user,
                                 @RequestBody CreateBasketRequest request) throws IOException {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        basketService.add(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    /**
     * 장바구니 조회
     */
    @GetMapping("/baskets")
    public ResponseEntity<?> basketList(@SessionAttribute(name = "user", required = false) User user) throws IOException {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        List<Basket> basketList = basketService.select(user);
        List<BasketListResponse> basketListResponses = basketList.stream()
                .map(basket -> new BasketListResponse(
                        basket.getId(),
                        basket.getProduct().getId(),
                        basket.getProduct().getSeller().getName(),
                        basket.getProduct().getName(),
                        basket.getProduct().getPrice(),
                        basket.getProduct().getProduct_detail(),
                        basket.getProduct().getImage()
                ))
        .toList();

        return ResponseEntity.ok(basketListResponses);
    }

    /**
     * 장바구니 개수 조회
     */
    @GetMapping("/baskets/count")
    public ResponseEntity<?> count(@SessionAttribute(name = "user", required = false) User user) throws IOException {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        Long cnt = basketService.count(user);

        return ResponseEntity.ok(cnt);
    }

    /**
     * 장바구니 삭제
     */
    @PostMapping("/baskets/delete")
    public ResponseEntity<?> delete(@SessionAttribute(name = "user", required = false) User user,
                                    @RequestBody DeleteBasketRequest request) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        basketService.delete(user, request);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
