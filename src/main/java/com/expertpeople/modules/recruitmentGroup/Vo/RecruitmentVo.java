package com.expertpeople.modules.recruitmentGroup.Vo;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.recruitmentGroup.EventType;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter@Setter
public class RecruitmentVo {
    private Long id;
    private String createName;

    private String title;

    private String workTitle;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime endEnrollmentDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Integer limitOfEnrollments;

    private EventType eventType;

    private int numberOfRemainSpot;

    private List<Enrollments> erollments;

    public RecruitmentVo(Recruitment recruitment){
        this.id=recruitment.getId();
        this.createName=recruitment.getCreateBy().getName();
        this.title=recruitment.getTitle();
        this.description=recruitment.getDescription();
        this.createTime=recruitment.getCreateTime();
        this.endEnrollmentDateTime=recruitment.getEndEnrollmentDateTime();
        this.startDateTime=recruitment.getStartDateTime();
        this.endDateTime=recruitment.getEndDateTime();
        this.limitOfEnrollments=recruitment.getLimitOfEnrollments();
        this.eventType=recruitment.getEventType();
        this.workTitle=recruitment.getWork().getTitle();
        this.numberOfRemainSpot=recruitment.numberOfRemainSpot();
    }
    @Getter@Setter
    public static class Enrollments{
        private Long id;
        private String name;

        private LocalDateTime enrolledAt;

        private boolean accepted;

        private boolean attended;

        public Enrollments(Enrollment e){
            this.name=e.getAccount().getName();
            this.id=e.getAccount().getId();
            this.accepted=e.isAccepted();
            this.attended=e.isAttended();
            this.enrolledAt=e.getEnrolledAt();
        }
    }



}
