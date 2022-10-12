package com.expertpeople.modules.work;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.job.Job;
import com.expertpeople.modules.zone.Zone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter@Setter
@EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Work {

    @Id @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<Account>managers=new HashSet<>();

    @ManyToMany
    private Set<Account>members=new HashSet<>();

    @Column(unique = true)
    private String path;

    private String title;

    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String fullDescription;

    @Lob@Basic(fetch = FetchType.EAGER)
    private String image;

    @ManyToMany
    private Set<Job> jobs=new HashSet<>();

    @ManyToMany
    private Set<Zone> zones=new HashSet<>();

    private LocalDateTime publishedDateTime;

    private LocalDateTime closeDateTime;

    private LocalDateTime recruitingUpdateTime;

    private boolean recruiting;

    private boolean published;

    private boolean close;

    private boolean useBanner;

    public void addManager(Account account) {
        this.managers.add(account);
    }

    public boolean isManager(Account account){
        return this.managers.contains(account);
    }
    public boolean isMember(Account account) {
        return this.members.contains(account);
    }
    public boolean isJoinable(Account account){
        return this.isPublished()&&this.recruiting &&!this.close
                &&!this.members.contains(account)&&!this.managers.contains(account);
    }


    public void publish() {
        if(!this.close&&!this.published){
            this.published=true;
            this.publishedDateTime=LocalDateTime.now();
        }else{
            throw new RuntimeException("이미 공개중인 일감 이거나, 종료된 일감입니다.");
        }
    }

    public void close() {
        if(!this.close&&this.published){
            this.close=true;
            this.closeDateTime=LocalDateTime.now();
        }else{
            throw new RuntimeException("이미 종료된 일감이거나, 공개되지 않은 일감입니다.");
        }
    }

    public void startRecruit() {
        if(this.canUpdateRecruit()&&!this.recruiting){
            this.recruiting=true;
            this.recruitingUpdateTime=LocalDateTime.now();
        }else{
            throw new RuntimeException("종료된 일감 이거나, 공개되지 않은 일감 이거나, 이미 구인이 시작된 일감입니다.");
        }
    }

    public boolean canUpdateRecruit() {
        if(this.recruitingUpdateTime==null){
            return !this.close&&this.published;
        }
        return !this.close&&this.published&&this.recruitingUpdateTime.isBefore(LocalDateTime.now().minusHours(1));
    }

    public void stopRecruit() {
        if(this.canUpdateRecruit()&&this.recruiting){
            this.recruiting=false;
            this.recruitingUpdateTime=LocalDateTime.now();
        }else{
            throw new RuntimeException("종료된 일감 이거나, 공개되지 않은 일감이거나,구인이 시작되지 않은 일감입니다.");
        }
    }

    public boolean isRemovable() {
        return !this.published;
    }
}
