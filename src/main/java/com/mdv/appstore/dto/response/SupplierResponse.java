package com.mdv.appstore.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SupplierResponse {
    Long id;
    String name;
    String phone;
    String address;
    Boolean isDeleted;
}
