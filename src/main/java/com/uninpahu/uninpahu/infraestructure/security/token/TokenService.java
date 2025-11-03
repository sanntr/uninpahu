package com.uninpahu.uninpahu.infraestructure.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uninpahu.uninpahu.domain.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secreto;
    public String crearToken(Usuario usuario){
        try {
            Algorithm algorithm=Algorithm.HMAC256(secreto);
            return JWT.create()
                    .withIssuer("uninpahutienda")
                    .withSubject(usuario.getNombreUsuario())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algorithm);
        }catch ( JWTCreationException e){
            return new RuntimeException("Errorr al generar el token",e).toString();
        }
    }
    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-05:00"));

    }


    public String getSubject(String token){
        try {
            var algoritmo = Algorithm.HMAC256(secreto);

            return JWT.require(algoritmo)
                    .withIssuer("uninpahutienda")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido o expirado!");
        }
    }
}
