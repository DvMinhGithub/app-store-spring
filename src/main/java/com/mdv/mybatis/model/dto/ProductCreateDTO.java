package com.mdv.mybatis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {
    String name;
    Double price;
    String imageUrl;
    Integer sold;
    Integer view;
    boolean isDeleted;
    Long brandId;
}
