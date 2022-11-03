package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.work.Work;
import lombok.Getter;

@Getter
public class WorkCreatedEvent {

    private Work work;

    public WorkCreatedEvent(Work work){
        this.work=work;
    }
}
