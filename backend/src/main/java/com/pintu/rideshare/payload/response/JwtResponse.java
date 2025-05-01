package com.pintu.rideshare.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nickname;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String email, String nickname, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.roles = roles;
    }
} 