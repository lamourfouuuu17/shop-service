package com.shopservice.shopservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Shop {
    private String name;
    private int price;
}

