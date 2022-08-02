package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest)throws Exception{
//        jwtService.authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());
//
//    }

}
