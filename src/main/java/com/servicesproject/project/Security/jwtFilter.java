package com.servicesproject.project.Security;

import com.servicesproject.project.services.userservice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class jwtFilter extends OncePerRequestFilter {
@Autowired
    TokenUtil tokenUtil;

@Autowired
userservice userservice;
    @Value("${token.header}")
    private String Token_Header;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       final  String header = request.getHeader(Token_Header);
        final SecurityContext securityContext= SecurityContextHolder.getContext();
        if(header != null && securityContext.getAuthentication() == null)
        {
            String token=header.substring("Bearer ".length());
            String username = tokenUtil.getUserName(token);

            if (username != null){
                UserDetails userDetails= userservice.loadUserByUsername(username);
                if(tokenUtil.isValid(token , userDetails)){
                    UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }


            }
        }
        filterChain.doFilter(request,response);
    }
}
