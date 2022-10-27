package com.expertpeople.modules.recruitmentGroup.validator;

import com.expertpeople.modules.recruitmentGroup.form.RecruitUpdateForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

public class RecruitUpdateValidator implements Validator {
    private static LocalDateTime nowTime=LocalDateTime.now();

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RecruitUpdateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecruitUpdateForm recruitUpdateForm=(RecruitUpdateForm) target;

        if(recruitUpdateForm.getEndDateTime().isBefore(nowTime)){
            errors.rejectValue("endDateTime","invalid.endDateTime",new Object[]{recruitUpdateForm.getEndEnrollmentDateTime()},"모집 종료 시간이 현재 시간 이전 입니다.");
        }

        if(recruitUpdateForm.getStartDateTime().isBefore(nowTime)){
            errors.rejectValue("startDateTime","invalid.startDateTime",new Object[]{recruitUpdateForm.getEndEnrollmentDateTime()},"일 시작 시간이 현재 시간 이전 입니다.");
        }

        if(recruitUpdateForm.getEndDateTime().isBefore(recruitUpdateForm.getStartDateTime())){
            errors.rejectValue("startDateTime","invalid.startDateTime",new Object[]{recruitUpdateForm.getEndEnrollmentDateTime()},"일 종료 시간이 일 시작 시간 이전 입니다.");
        }
    }
}
