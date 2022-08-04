package com.expertpeople.infra.jwt;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account= accountRepository.findByEmail(email);

        if(account==null){
            throw new UsernameNotFoundException(email);
        }

        return new User(account.getEmail(), account.getPassword(),
                new ArrayList<>());
    }

}
