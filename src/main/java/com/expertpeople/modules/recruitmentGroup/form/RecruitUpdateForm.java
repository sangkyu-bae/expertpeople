package com.expertpeople.modules.recruitmentGroup.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class RecruitUpdateForm {
    @Length(max = 50)
    private String title;

    @NotBlank
    private String description;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endEnrollmentDateTime;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;

    private Integer limitOfEnrollments;

    @NotBlank
    private String eventType;
}
