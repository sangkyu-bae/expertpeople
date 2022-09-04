package com.expertpeople.modules.account.validator;

import com.expertpeople.modules.account.form.PasswordForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PasswordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PasswordForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordForm passwordForm=(PasswordForm) target;

        if(!passwordForm.getPassword().equals(passwordForm.getPasswordCheck())){
            errors.rejectValue("password","wrong,password value","패스워드가 같지 않습니다.");
        }
    }
}
