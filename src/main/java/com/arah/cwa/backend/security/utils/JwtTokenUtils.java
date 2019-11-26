package com.arah.cwa.backend.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class JwtTokenUtils {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final long ACCESS_TOKEN_VALIDITY = 3600 * 60 * 60;
    private static final String SIGNING_KEY = "qwerty2019";
    private static final String AUTHORITIES_KEY = "scopes";

    public boolean isValid(String token) {
        return token != null && token.startsWith(TOKEN_PREFIX);
    }

    public boolean isExpired(String token) {
        final Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    public String removePrefix(String token) {
        return token != null ? token.replace(TOKEN_PREFIX, "") : null;
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
