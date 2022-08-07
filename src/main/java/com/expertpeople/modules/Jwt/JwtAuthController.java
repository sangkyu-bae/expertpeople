package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.account.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest)throws Exception{
         jwtService.authenticate(modelMapper.map(authenticationRequest, Account.class));

         final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(authenticationRequest.getEmail());
        System.out.println(userDetails);
         final String token =jwtTokenUtil.generateToken(userDetails);

         return ResponseEntity.ok(new JwtResponse(token));
    }

}
