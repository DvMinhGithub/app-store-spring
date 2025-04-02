package com.mdv.appstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SupplierRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be exactly 10 digits")
    private String phone;

    @Size(max = 500, message = "Address must be less than 500 characters")
    private String address;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;
}
