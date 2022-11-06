package com.expertpeople.modules.notification.emitter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

@Getter
@Setter
public class ResponseEmitter {
    private String id;
    private SseEmitter sseEmitter;

}
