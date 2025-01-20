package com.mdv.mybatis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String name;
    Double price;
    String imageUrl;
    Integer sold;
    Integer view;
    boolean hasDeleted;
    BrandDTO brand;
}
