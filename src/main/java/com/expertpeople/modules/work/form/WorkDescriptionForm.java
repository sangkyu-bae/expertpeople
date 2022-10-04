package com.expertpeople.modules.work.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class WorkDescriptionForm {

    @NotBlank
    @Length(max=100)
    private String shortDescription;

    @NotBlank
    private String fullDescription;
}
