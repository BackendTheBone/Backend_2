package com.nps.coco.domain.basket.service;

import com.nps.coco.domain.basket.dto.CreateBasketRequest;
import com.nps.coco.domain.basket.dto.DeleteBasketRequest;
import com.nps.coco.domain.basket.entity.Basket;
import com.nps.coco.domain.basket.repository.BasketRepository;
import com.nps.coco.domain.product.entity.Product;
import com.nps.coco.domain.product.entity.ProductStatus;
import com.nps.coco.domain.product.repository.ProductRepository;
import com.nps.coco.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    /**
     * 장바구니 등록
     */
    @Transactional
    public Long add(User user, CreateBasketRequest request) {

        Optional<Product> product = productRepository.findById(request.getProductId());
        validateProduct(product); // 선택 상품 유효성 검증

        // 현재 상품이 이미 장바구니에 있는 경우
        checkProductInBasket(product.orElseThrow(),user);

        Basket basket = Basket.builder()
                .user(user)
                .product(product.orElseThrow()) //Optional -> 객체 변환
                .build();

        basketRepository.save(basket); // DB 저장

        return basket.getId(); // id 반환하기
    }

    /**
     * 장바구니 조회
     */
    @Transactional
    public List<Basket> select(User user) {

        // 상품이 INACTIVE인 경우도 같이 조회해야하는게 맞나?
        List<Basket> baskets = basketRepository.findBasketsByUser(user);

        return baskets;
    }

    /**
     * 장바구니 개수 조회
     */
    @Transactional
    public Long count(User user) {
        return basketRepository.countByUser(user);
    }

    /**
     * 장바구니 삭제
     */
    @Transactional
    public void delete(User user, DeleteBasketRequest deleteBasketRequest) {
        // 코드에서 유효성 처리를 해주는게 좋은것인가?
        // 그런데 list안의 갯수가 많을 시에는 db where절로 그냥 걸러주는게 더 빠르지 않을까?
        // 장바구니를 여러개 삭제할 때, user가 맞지 않는 basket이 있을때 바로 에러처리 해주는 게 맞는 것인가?

        validateDeletionPermissions(deleteBasketRequest.getBasketIdList(),user);

        // list id 전체 삭제
        basketRepository.deleteAllByIds(deleteBasketRequest.getBasketIdList(), user.getUserId());
    }

    // 상품이 해당 db에 있는지 확인
    private void validateProduct(Optional<Product> product) {
        // DB에 상품이 있고, 삭제 여부 확인
        if (product.isEmpty() || product.get().getStatus() == ProductStatus.INACTIVE) {
            throw new IllegalStateException("");
        }
    }

    // 상품이 장바구니에 있는지 확인
    private void checkProductInBasket(Product product,User user) {

        Optional<Basket> basket = basketRepository.findBasketByUserAndProduct(user,product);

        // 현재 상품이 이미 장바구니에 있는 경우
        if (basket.isPresent()) {
            throw new IllegalStateException("");
        }
    }

    // 삭제 요청된 ID 목록이 유효한지, 사용자가 해당 바스켓 ID에 대한 삭제 권한을 가지고 있는지 확인
    private void validateDeletionPermissions(List<Long> basketIds,User user) {
        // 유효성 검사 및 삭제 권한 확인 로직 구현
        basketIds.forEach(id -> {
            Optional<Basket> basket = basketRepository.findById(id);

            // 삭제 요청된 ID가 유효한지 체크
            if (basket.isEmpty()) throw new IllegalStateException("");

            // 사용자가 해당 바스켓 ID에 대한 삭제 권한을 가지고 있는지 확인
            if(user != basket.get().getUser()){
                throw new IllegalStateException("");
            }
        });
    }

}
