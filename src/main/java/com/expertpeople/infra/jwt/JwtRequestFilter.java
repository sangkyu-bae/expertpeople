package com.expertpeople.infra.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailService jwtUserDetailService;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader= request.getHeader("Authorization");

        String username="";
        String jwtToken="";

        if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer")){
            jwtToken=requestTokenHeader.substring(7);
            try{
                username=jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
               throw new Exception("알수 없는 토큰 입니다.");
            }catch (ExpiredJwtException e){
                throw new EOFException("만료된 토큰 입니다.");
            }
        }else{
            throw new Exception("잘못된 입력입니다.");
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);

            if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
