package com.videogametracker.user.Model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String userId;
    private String username;
    private String password;
    private String errorMessage;
    private String token;

    // kafka related
//    private String correlationId;
}
