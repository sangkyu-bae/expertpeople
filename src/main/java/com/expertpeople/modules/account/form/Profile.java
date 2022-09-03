package com.expertpeople.modules.account.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Profile {

    private String bio;

    private int career;

    @Length(max=50)
    private String job;

    private String profileImage;

    private String location;

}
