package com.agency.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType;

}
