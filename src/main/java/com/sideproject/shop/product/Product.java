package com.sideproject.shop.product;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private int id;
    private String name;
    private int price;
    private String categoryCode;
    private String categoryName;
    private String sizeInfo;
    private String content;
    private int hit;
    private int stocks;
    private String thumbnail;
    private List<String> images;
    private LocalDateTime insertDate;
}
