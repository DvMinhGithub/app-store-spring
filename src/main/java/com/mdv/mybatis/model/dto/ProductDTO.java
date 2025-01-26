package com.mdv.mybatis.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    Long totalQuantity;
    Integer sold;
    Integer view;

    @JsonProperty("isDeleted")
    boolean isDeleted;

    BrandDTO brand;
}
