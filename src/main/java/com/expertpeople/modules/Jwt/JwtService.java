package com.expertpeople.modules.Jwt;

import com.expertpeople.modules.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    public void authenticate(Account account) throws Exception {
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    account.getEmail(),
                    account.getPassword(),
                    List.of(new SimpleGrantedAuthority(account.getRole())));
            SecurityContextHolder.getContext().setAuthentication(token);
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
    }
}
