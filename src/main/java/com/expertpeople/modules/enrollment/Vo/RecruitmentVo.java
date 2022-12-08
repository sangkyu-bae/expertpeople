package com.expertpeople.modules.enrollment.Vo;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.recruitmentGroup.EventType;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import com.expertpeople.modules.work.Work;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RecruitmentVo {

    private Long id;

    private Long workId;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime endDateTime;

    public RecruitmentVo(Enrollment enrollment){
        Recruitment recruitment=enrollment.getRecruitment();
        this.id=recruitment.getId();
        this.workId=recruitment.getWork().getId();
        this.title=recruitment.getTitle();
        this.description=recruitment.getDescription();
        this.createTime=recruitment.getCreateTime();
        this.endDateTime=recruitment.getEndDateTime();
    }

}
