package com.mdv.appstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserUpdateRequest {
    String address;
    String avatar;
    String dob;

    @Email(message = "Invalid email address")
    String email;

    String name;

    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    String phone;
}
