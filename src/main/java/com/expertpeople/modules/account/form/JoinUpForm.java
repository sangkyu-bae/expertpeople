package com.expertpeople.modules.account.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
public class JoinUpForm {

    @NotBlank
    @Length(min=3,max=20)
    @Pattern(regexp="^[ㄱ-ㅎ가-힣a-z0-9]{3,20}$")
    private String nickname;

    @NotBlank
    @Length(min=2,max=20)
    @Pattern(regexp ="^[ㄱ-ㅎ가-힣]{2,20}")
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min=7,max=30)
    private String password;
//
//    @NotBlank
//    private String address;

}
