package com.expertpeople.infra.jwt;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return new UserAccount(account);
    }

}
