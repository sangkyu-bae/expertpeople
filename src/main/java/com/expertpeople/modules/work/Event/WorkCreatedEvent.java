package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.work.Work;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkCreatedEvent {

    private final Work work;

}
