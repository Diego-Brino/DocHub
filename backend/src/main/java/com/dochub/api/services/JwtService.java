package com.dochub.api.services;

import com.dochub.api.utils.Constants;
import com.dochub.api.utils.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.secret.key}")
    private String SECRET_KEY;

    public String extractUserEmail (final String token) {
        if (token.startsWith("Bearer ")) {
            return _extractClaim(Utils.removeBearerPrefix(token), Claims::getSubject);
        }

        return _extractClaim(token, Claims::getSubject);
    }

    public String generateToken (final UserDetails userDetails) {
        return generateToken(Collections.emptyMap(), userDetails);
    }

    public String generateToken (final Map<String, Object> extraClaims, final UserDetails userDetails) {
        return Jwts
            .builder()
            .claims(extraClaims)
            .issuer(Constants.SYSTEM_NAME)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME_MILLIS))
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
        final byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}