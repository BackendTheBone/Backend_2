package com.nps.coco.domain.basket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DeleteBasketRequest {

    @NotNull
    private List<Long> basketIdList;
}
