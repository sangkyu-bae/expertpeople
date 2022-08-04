package com.expertpeople.modules.Jwt;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class JwtResponse implements Serializable {
    private final String jwttoken;

    public JwtResponse(String jwtToken){
        this.jwttoken=jwtToken;
    }
}
