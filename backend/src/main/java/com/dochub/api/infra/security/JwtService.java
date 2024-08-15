package com.dochub.api.infra.security;

import com.dochub.api.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public String extractUserEmail (final String token) {
        return _extractClaim(token, Claims::getSubject);
    }

    public String generateToken (final UserDetails userDetails) {
        return generateToken(Collections.emptyMap(), userDetails);
    }

    public String generateToken (final Map<String, Object> extraClaims, final UserDetails userDetails) {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(_getSecretKey(), Jwts.SIG.HS256)
            .compact();
    }

    public Boolean isTokenValid (final String token, final UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);

        return (userEmail.equals(userDetails.getUsername()) && !_isTokenExpired(token));
    }

    private Boolean _isTokenExpired (final String token) {
        return _extractExpiration(token).before(new Date());
    }

    private Date _extractExpiration (final String token) {
        return _extractClaim(token, Claims::getExpiration);
    }

    private <T> T _extractClaim (final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = _extractPayloadFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Claims _extractPayloadFromToken (final String token) {
        return Jwts
            .parser()
            .verifyWith(_getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey _getSecretKey () {
        final byte[] keyBytes = Decoders.BASE64.decode(Constants.SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}