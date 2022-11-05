package com.expertpeople.infra.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
       if(request instanceof ServletServerHttpRequest){
           ServletServerHttpRequest servletServerHttpRequest=(ServletServerHttpRequest) request;
           HttpSession httpSession=servletServerHttpRequest.getServletRequest().getSession();
           attributes.put("sessionId",httpSession.getId());
       }
        return true;
    }
}
