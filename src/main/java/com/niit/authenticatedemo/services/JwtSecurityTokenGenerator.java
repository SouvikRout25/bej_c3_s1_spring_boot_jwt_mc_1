package com.niit.authenticatedemo.services;

import com.niit.authenticatedemo.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtSecurityTokenGenerator implements ServiceTokenGenerator{
    @Override
    public Map<String, String> generateToken(User user) {
        String jwToken = null;
        jwToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"mykey").compact();
        Map<String, String> map = new HashMap<>();
        map.put("token",jwToken);
        map.put("message","User logged in!!");
        return map;
    }
}