package com.mdv.appstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeResponse {
    private Long id;
    private String attributeName;
    private String attributeValue;
    private Long productId;
}
