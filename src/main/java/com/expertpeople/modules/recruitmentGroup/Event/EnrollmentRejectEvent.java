package com.expertpeople.modules.recruitmentGroup.Event;

import com.expertpeople.modules.enrollment.Enrollment;

public class EnrollmentRejectEvent extends EnrollmentEvent{
    public EnrollmentRejectEvent(Enrollment enrollment) {
        super(enrollment,"구인 신청이 취소 되었습니다. 확인하세요" );
    }
}
