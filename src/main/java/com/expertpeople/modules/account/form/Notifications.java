package com.expertpeople.modules.account.form;

import com.expertpeople.modules.account.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@NoArgsConstructor
public class Notifications {
    private boolean workCreateByEmail;

    private boolean workCreateByWeb;

    private boolean workEnrollmentResultByEmail;

    private boolean workEnrollmentResultByWeb;

    private boolean workUpdateByEmail;

    private boolean workUpdateByWeb;

    public Notifications(Account account){
        this.workCreateByEmail=account.isWorkCreateByEmail();
        this.workCreateByWeb=account.isWorkCreateByWeb();
        this.workEnrollmentResultByEmail=account.isWorkEnrollmentResultByEmail();
        this.workEnrollmentResultByWeb=account.isWorkEnrollmentResultByWeb();
        this.workUpdateByEmail=account.isWorkUpdateByEmail();
        this.workUpdateByWeb=account.isWorkUpdateByWeb();
    }

}
