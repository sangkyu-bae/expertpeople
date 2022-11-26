package com.expertpeople.modules.work.Vo;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.work.Work;
import com.expertpeople.modules.zone.Zone;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter @Setter
public class fetchWorkVo {
    private Long id;
    private LocalDateTime publishedDateTime;

    private String title;

    private String shortDescription;

    private Set<Accounts> members;

    private Set<Job> jobs;

    private Set<Zone> zones;

    private String path;
    public fetchWorkVo(Work work){
        this.id=work.getId();
        this.publishedDateTime=work.getPublishedDateTime();
        this.title=work.getTitle();
        this.shortDescription=work.getShortDescription();
        this.path=work.getPath();
        this.jobs=work.getJobs();
        this.zones=work.getZones();

        Set<Accounts> accounts=work.getMembers().stream().map(Accounts::new).collect(Collectors.toSet());
        this.members=accounts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Accounts{
        private String name;

        private String profileImage;

        private String bio;

        public Accounts(Account account){
            this.name=account.getName();
            this.profileImage=account.getProfileImage();
            this.bio=account.getBio();
        }

    }
}
