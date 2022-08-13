package com.expertpeople.modules.Jwt;

import com.expertpeople.infra.jwt.JwtTokenUtil;
import com.expertpeople.infra.jwt.JwtUserDetailService;
import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtTokenUtil  jwtTokenUtil;

    public void authenticate(Account account) throws Exception {
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    new UserAccount(account),
                    account.getPassword(),
                    List.of(new SimpleGrantedAuthority(account.getRole())));
            SecurityContextHolder.getContext().setAuthentication(token);
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
    }

    public JwtResponse getJwtResponse(Account account) throws Exception {
        authenticate(account);
        final UserDetails userDetails=jwtUserDetailService.loadUserByUsername(account.getEmail());
        final String token=jwtTokenUtil.generateToken(userDetails);

        JwtResponse jwtResponse=JwtResponse.builder()
                .id(account.getEmail())
                .name(account.getName())
                .token(token)
                .build();
        return jwtResponse;
    }
}
