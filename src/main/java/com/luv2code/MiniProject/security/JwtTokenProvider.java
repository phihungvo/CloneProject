package com.luv2code.MiniProject.security;

import com.luv2code.MiniProject.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.app-jwt-expiration-milliseconds}")
    private String jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expiryTime = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryTime)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SIGNER_KEY));
    }

    public String getUsername(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException exception){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException exception){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch (UnsupportedJwtException exception){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalIdentifierException exception){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
