package com.mdv.appstore.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserDTO {
    Long id;
    String address;
    String avatar;
    String dob;
    String email;
    String name;
    String password;
    String phone;
    List<RoleDTO> roles;
}
