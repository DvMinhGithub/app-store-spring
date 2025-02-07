package com.mdv.appstore.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SupplierDTO {
    Long id;
    String name;
    String phone;
    String address;
    Boolean isDeleted;
}
