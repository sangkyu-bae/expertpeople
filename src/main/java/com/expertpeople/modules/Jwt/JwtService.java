package com.expertpeople.modules.Jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    public void authenticate(String username, String password) {
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    new UserAccount(account),
//                    account.getPassword(),
//                    List.of(new SimpleGrantedAuthority(account.getRole())));)
//        }
    }
}
