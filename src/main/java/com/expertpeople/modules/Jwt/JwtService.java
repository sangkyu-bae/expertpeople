package com.expertpeople.modules.Jwt;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    public void authenticate(Account account) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    new UserAccount(account),
                    account.getPassword(),
                    List.of(new SimpleGrantedAuthority(account.getRole()))));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
    }
}
