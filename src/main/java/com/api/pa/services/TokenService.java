package com.api.pa.services;


import com.api.pa.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String createToken(User user) {

        return JWT.create()
                .withIssuer("products")
                .withSubject(user.getUsername())
                .withClaim("id", user.getUserId())
                .withClaim("role", user.getRole())
                .withExpiresAt(LocalDateTime.now().plusMinutes(140).toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256("secreta"));
    }

    public String getSubject(String token) {

        return JWT.require(Algorithm.HMAC256("secreta"))
                .withIssuer("products")
                .build().verify(token).getSubject();
    }
}
