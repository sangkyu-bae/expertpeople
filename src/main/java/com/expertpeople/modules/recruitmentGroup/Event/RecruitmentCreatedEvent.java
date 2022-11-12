package com.expertpeople.modules.recruitmentGroup.Event;


import com.expertpeople.modules.recruitmentGroup.Recruitment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecruitmentCreatedEvent {
    private final Recruitment recruitment;
}
