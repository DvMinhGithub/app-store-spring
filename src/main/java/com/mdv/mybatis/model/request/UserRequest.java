package com.mdv.mybatis.model.request;

import java.time.LocalDateTime;
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
public class UserRequest {
    String address;
    String avatar;
    String dob;
    String email;
    String name;
    String password;
    String phone;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
