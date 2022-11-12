package com.expertpeople.modules.work.Event;

import com.expertpeople.modules.work.Work;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter
@RequiredArgsConstructor
public class WorkUpdateEvent {
    private final Work work;

    private final String message;
}
