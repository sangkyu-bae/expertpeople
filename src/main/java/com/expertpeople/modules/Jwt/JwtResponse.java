package com.expertpeople.modules.Jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@Builder
public class JwtResponse implements Serializable {

    private String token;

    private String name;

    private String id;

    private String profileImage;
}
