package com.expertpeople.modules.work.Vo;

import com.expertpeople.modules.account.Account;
import com.expertpeople.modules.work.Work;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter @Setter
public class fetchWorkVo {
    private Long id;
    private LocalDateTime publishedDateTime;

    private String title;

    private String shortDescription;

    private Set<Account> members;

    private String path;
    public fetchWorkVo(Work work){
        this.id=work.getId();
        this.publishedDateTime=work.getPublishedDateTime();
        this.title=work.getTitle();
        this.shortDescription=work.getShortDescription();
        this.members=work.getMembers();
        this.path=work.getPath();
    }
}
