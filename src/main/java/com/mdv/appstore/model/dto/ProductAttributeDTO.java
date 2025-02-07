package com.mdv.appstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDTO {
    private Long id;
    private String attributeName;
    private String attributeValue;
    private Long productId;
}
