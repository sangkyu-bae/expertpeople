package com.expertpeople.modules.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordForm {

    @NotBlank
    @Length(min=7,max = 30)
    private String password;

    @NotBlank
    @Length(min = 7,max = 30)
    private String passwordCheck;
}
