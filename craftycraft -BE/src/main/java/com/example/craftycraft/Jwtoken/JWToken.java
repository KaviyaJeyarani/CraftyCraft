package com.example.craftycraft.Jwtoken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Key;
import java.util.Date;


@Component
public class JWToken {
//    private final Key secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);
      private  String secretKey = "yourSecretKey";
    public  String generatingToken(String mailId, String fullName){
        return Jwts.builder()
                .setSubject(mailId)
                .claim("NAME",fullName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extractdetail(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch(HttpClientErrorException.Unauthorized exception){
            return false;
        }
    }
}

