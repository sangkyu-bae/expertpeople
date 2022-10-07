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
    private Set<Zone> zones;

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
}
