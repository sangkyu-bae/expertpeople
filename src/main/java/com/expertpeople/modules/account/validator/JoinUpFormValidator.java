package com.expertpeople.modules.account.validator;

import com.expertpeople.modules.account.AccountRepository;
import com.expertpeople.modules.account.form.JoinUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinUpForm joinUpForm=(JoinUpForm)target;

        if(accountRepository.existsByEmail(joinUpForm.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{joinUpForm.getEmail()},"이미 존재하는 이메일 입니다.");
        }

    }
}
