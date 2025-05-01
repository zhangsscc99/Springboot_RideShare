package com.pintu.rideshare.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotBlank
    private String verificationCode;
    
    private String nickname;
} 