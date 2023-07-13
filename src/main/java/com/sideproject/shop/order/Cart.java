package com.sideproject.shop.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cart {
    private String userId;
    private String productId;
    private String productName;
    private String sizeCode;
    private String sizeName;
    private String thumbnail;
    private int cnt;
}
