package com.mdv.appstore.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String accessToken;
    private String refreshToken;
}
