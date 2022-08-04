package com.expertpeople.modules.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor
public class JwtRequest {

    private String email;
    private String password;

}
