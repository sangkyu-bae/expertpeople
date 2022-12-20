package com.expertpeople.modules.enrollment.Vo;

import com.expertpeople.modules.enrollment.Enrollment;
import com.expertpeople.modules.recruitmentGroup.Recruitment;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class RecruitmentVo {

    private Long id;

    private String workPath;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime endDateTime;

    private Long recruitId;

    private String workDescription;

    public RecruitmentVo(Enrollment enrollment){
        Recruitment recruitment=enrollment.getRecruitment();
        this.id=recruitment.getId();
        this.workPath=recruitment.getWork().getPath();
        this.title=recruitment.getTitle();
        this.description=recruitment.getDescription();
        this.createTime=recruitment.getCreateTime();
        this.endDateTime=recruitment.getEndDateTime();
        this.recruitId=recruitment.getId();
        this.workDescription=recruitment.getWork().getShortDescription();
    }

}
