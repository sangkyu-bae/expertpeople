package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenProvider;
import com.expertpeople.modules.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtAuthController {
    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authenticationRequest)throws Exception{
         Account checkLogin;
         JwtResponse jwtResponse = null;
         try {
             checkLogin= jwtService.getAccount(authenticationRequest);
             jwtResponse=jwtService.getJwtResponse(checkLogin,true);
         }catch (Exception e){
            return ResponseEntity.badRequest().build();
         }
        return ResponseEntity.ok(jwtResponse);
    }
}
