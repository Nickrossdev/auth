package com.nickross.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${SECRET_KEY}")
    private String secretKey;
    @Value("${token.expiration}")
    private Integer tokenExpiration;

    public String generateToken(UUID id) {
        return JWT.create()
                .withSubject(id.toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public UUID getSubject(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        verifyExpirationToken(decodedJWT);
        String subject = decodedJWT.getSubject();
        try {
            return UUID.fromString(subject);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El sujeto del token no es un UUID válido", e);
        }
    }

    private DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
        } catch (JWTDecodeException e) {
            throw new RuntimeException("Error al decodificar el token", e);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Firma del token no válida", e);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Error al verificar el token", e);
        } catch (Exception e) {
            throw new RuntimeException("Error desconocido al verificar el token", e);
        }
    }

    private void verifyExpirationToken(DecodedJWT decodedJWT) {
        Date expirationDate = decodedJWT.getExpiresAt();
        if (expirationDate == null) {
            throw new InvalidClaimException("El claim 'exp' no está presente en el token");
        }
        Instant expirationInstant = expirationDate.toInstant();
        if (expirationInstant.isBefore(Instant.now())) {
            throw new TokenExpiredException("El token ha expirado", expirationInstant);
        }
    }
}
