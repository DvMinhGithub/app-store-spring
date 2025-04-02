package com.mdv.appstore.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateResponse {
    String name;
    Double price;
    String imageUrl;
    Integer sold;
    Integer view;
    boolean isDeleted;
    Long brandId;
}
