package com.niit.authenticatedemo.services;

import com.niit.authenticatedemo.domain.User;
import org.springframework.context.annotation.Bean;

import java.util.Map;

public interface ServiceTokenGenerator {
    Map<String,String> generateToken(User user);
}