package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private long expiryTime;

    private Algorithm algorithm;

    private static final String USER_NAME="Username";

    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
       algorithm= Algorithm.HMAC256(algorithmkey);
    }

    //Computer Engineer is inemployed
    public String generateToken(AppUser user){
        return JWT.create().
                withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    //Jokey Rokey with Bodybuilder Vikram  J R W B V
    public String getUserName(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
