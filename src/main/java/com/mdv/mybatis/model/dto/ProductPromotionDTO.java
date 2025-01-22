package com.mdv.mybatis.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductPromotionDTO {
    Long id;
    Long productId;
    Date endTime;
    Date startTime;
    Double discountPrice;
    Boolean isActive;
}
