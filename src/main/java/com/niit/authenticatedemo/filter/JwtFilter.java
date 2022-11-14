package com.niit.authenticatedemo.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtFilter extends GenericFilterBean{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //ServletOutputStream outputStream = response.getOutputStream();

        String authHeader = request.getHeader("authorization");

        if(authHeader==null|| !authHeader.startsWith("Bearer")){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            outputStream.println("Missing or invalid Token ");
//            outputStream.close();
            throw new ServletException("Missing or Invalid exception");

        }else {
            String jwtToken = authHeader.substring(7);
//            String username= Jwts.parser().setSigningKey("mykey").parseClaimsJws(jwtToken).getBody().getSubject();
//            request.setAttribute("username",username);
            Claims claims = Jwts.parser().setSigningKey("mykey").parseClaimsJws(jwtToken).getBody();
            System.out.println("\nclaims : " + claims);

            request.setAttribute("claims", claims);
            filterChain.doFilter(request,response);
        }
    }
}