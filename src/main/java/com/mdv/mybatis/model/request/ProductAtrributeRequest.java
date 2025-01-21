package com.mdv.mybatis.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAtrributeRequest {
    @NotBlank(message = "Attribute name is required")
    @Size(max = 255, message = "Attribute name must be less than 255 characters")
    private String attributeName;

    @NotBlank(message = "Attribute value is required")
    @Size(max = 255, message = "Attribute value must be less than 255 characters")
    private String attributeValue;

    @NotNull(message = "Product ID is required")
    private Long productId;
}
