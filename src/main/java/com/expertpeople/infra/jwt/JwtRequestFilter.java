package com.expertpeople.infra.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends GenericFilter {
    @Autowired
    private JwtUserDetailService jwtUserDetailService;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    private final JwtTokenProvider jwtTokenProvider;

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        final String requestTokenHeader= jwtTokenProvider.resolveToken((HttpServletRequest) request);
//
//        String username="";
//        String jwtToken="";
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        } else {
//            logger.warn("JWT Token does not begin with Bearer String");
//        }
//
//        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
//
//            if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
//                        userDetails,null,userDetails.getAuthorities()
//                );
//
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        chain.doFilter(request,response);
//    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String requestTokenHeader= request.getHeader("Authorization");
//
//        String username="";
//        String jwtToken="";
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        } else {
//            logger.warn("JWT Token does not begin with Bearer String");
//        }
//
//        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
//            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
//
//            if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
//                        userDetails,null,userDetails.getAuthorities()
//                );
//
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//    }
}
