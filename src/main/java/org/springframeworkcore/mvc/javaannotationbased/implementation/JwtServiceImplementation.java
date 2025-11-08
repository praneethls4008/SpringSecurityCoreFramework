package org.springframeworkcore.mvc.javaannotationbased.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@PropertySource("classpath:application.properties")
public class JwtServiceImplementation implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration; // milliseconds

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration; // milliseconds

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Map<String, String> generateAccessAndRefreshTokens(UserDetails userDetails) {
        String accessToken = createToken(userDetails, accessTokenExpiration);
        String refreshToken = createToken(userDetails, refreshTokenExpiration);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    private String createToken(UserDetails userDetails, long expiryMillis) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String refreshAccessToken(String refreshToken, UserDetails userDetails) {
        Claims claims = extractAllClaims(refreshToken);
        if (isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token expired");
        }

        String username = claims.getSubject();

        return createToken(userDetails, accessTokenExpiration);
    }




    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        try{
            final Claims claims = extractAllClaims(token);
            return resolver.apply(claims);
        } catch (Exception e) {
            System.out.println("exception caught:"+e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
