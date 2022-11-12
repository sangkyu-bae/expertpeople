package com.expertpeople.modules.recruitmentGroup.Event;

import com.expertpeople.modules.enrollment.Enrollment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class EnrollmentEvent {

    protected final Enrollment enrollment;

    protected final String message;
}
