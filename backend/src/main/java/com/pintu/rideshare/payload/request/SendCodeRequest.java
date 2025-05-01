package com.pintu.rideshare.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SendCodeRequest {
    @NotBlank
    @Size(max = 50)
    @Email(message = "邮箱格式不正确")
    private String email;
} 