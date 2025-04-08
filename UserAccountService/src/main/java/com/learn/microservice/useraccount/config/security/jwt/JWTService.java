package com.learn.microservice.useraccount.config.security.jwt;


import com.learn.microservice.useraccount.config.web.ApplicationProperties;
import com.learn.microservice.useraccount.services.user.auth.AuthResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static com.learn.microservice.useraccount.AppConstants.*;


@Component
@RequiredArgsConstructor
public class JWTService {

    @Autowired
    private ApplicationProperties applicationProperties;

    public AuthResponseDTO generateToken(String username, boolean isAuthentication) {

        if(isAuthentication) {
            var claims = new HashMap<String, Object>();

            var token = createToken(username, claims);
            String refreshToken = generateRefreshToken(username);

            return new AuthResponseDTO(token, refreshToken);
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    public String getAuthKey(HttpServletRequest request) {
        return request.getHeader(AUTH_KEY);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(String username, HashMap<String, Object> claims) {

        var createTime = new Date(System.currentTimeMillis());
        var expiryTime = new Date(System.currentTimeMillis() + TOKEN_EXPIRY); // 1 Hrs

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createTime)
                .setExpiration(expiryTime)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRY); // 12 Hrs

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getRefreshKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * @return
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getKey());
        return Keys.hmacShaKeyFor(keyBytes) ;
    }

    /**
     *
     * @return
     */
    private Key getRefreshKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getRefreshToken());
        return Keys.hmacShaKeyFor(keyBytes) ;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
