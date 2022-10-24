package com.expertpeople.modules.work.Vo;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.zone.Zone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class WorkVo {

    private Long id;

    private String path;

    private String title;

    private String shortDescription;

    private String fullDescription;

    private String image;

    private LocalDateTime publishedDateTime;

    private LocalDateTime closeDateTime;

    private LocalDateTime recruitingUpdateTime;

    private boolean recruiting;

    private boolean published;

    private boolean close;

    private boolean useBanner;

    private Set<Accounts>managers=new HashSet<>();

    private Set<Accounts>members=new HashSet<>();

    @Getter
    @Setter
    @NoArgsConstructor @AllArgsConstructor
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
