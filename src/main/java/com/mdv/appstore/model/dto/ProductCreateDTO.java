package com.mdv.appstore.model.dto;

import lombok.*;

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
