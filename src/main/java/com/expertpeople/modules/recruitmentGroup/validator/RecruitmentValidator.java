package com.expertpeople.modules.recruitmentGroup.validator;

import com.expertpeople.modules.recruitmentGroup.form.RecruitForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecruitmentValidator implements Validator {

    private static LocalDateTime nowTime=LocalDateTime.now();

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RecruitForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecruitForm recruitForm=(RecruitForm) target;

        if(recruitForm.getEndDateTime().isBefore(nowTime)){
            errors.rejectValue("endDateTime","invalid.endDateTime",new Object[]{recruitForm.getEndEnrollmentDateTime()},"모집 종료 시간이 현재 시간 이전 입니다.");
        }

        if(recruitForm.getStartDateTime().isBefore(nowTime)){
            errors.rejectValue("startDateTime","invalid.startDateTime",new Object[]{recruitForm.getEndEnrollmentDateTime()},"일 시작 시간이 현재 시간 이전 입니다.");
        }

        if(recruitForm.getEndDateTime().isBefore(recruitForm.getStartDateTime())){
            errors.rejectValue("startDateTime","invalid.startDateTime",new Object[]{recruitForm.getEndEnrollmentDateTime()},"일 종료 시간이 일 시작 시간 이전 입니다.");
        }
    }
}
