package ru.javaprojects.energybalancecontrolshared.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtProvider {
    public static final String AUTHORIZATION_TOKEN_HEADER = "Authorization-Token";
    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    private final Environment environment;

    public JwtProvider(Environment environment) {
        this.environment = environment;
    }

    public  String generateAuthorizationToken(String userId, String ... authorities) {
        return JWT.create()
                .withIssuer(environment.getProperty("authorization.token.issuer"))
                .withAudience(environment.getProperty("authorization.token.audience"))
                .withIssuedAt(new Date())
                .withSubject(userId)
                .withArrayClaim(AUTHORITIES, authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("authorization.token.expiration-time"))))
                .sign(Algorithm.HMAC512(Objects.requireNonNull(environment.getProperty("jwt.secretKey"))));
    }

    public String getSubject(String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        return jwtVerifier.verify(token).getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public boolean isTokenValid(String subject, String token) {
        return !subject.isEmpty() && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        Date expirationDate = jwtVerifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC512(Objects.requireNonNull(environment.getProperty("jwt.secretKey")))).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}