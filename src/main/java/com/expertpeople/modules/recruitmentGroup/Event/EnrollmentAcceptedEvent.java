package com.expertpeople.modules.recruitmentGroup.Event;

import com.expertpeople.modules.enrollment.Enrollment;

public class EnrollmentAcceptedEvent extends EnrollmentEvent{
    public EnrollmentAcceptedEvent(Enrollment enrollment) {
        super(enrollment, "구인 신청을 확인했습니다. 일에 참가 하세요");
    }
}
