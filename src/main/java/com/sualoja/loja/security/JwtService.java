package com.sualoja.loja.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "sua_chave_secreta_grande_para_token";

    public String gerarToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }



    public String extrairEmail(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public String extrairRole(String token) {
        return extrairClaim(token, claims -> claims.get("role", String.class));
    }


    public boolean validarToken(String token, String email) {
        return email.equals(extrairEmail(token)) && !tokenExpirado(token);
    }

    private <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()  // Usando o método 'parser()'
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private boolean tokenExpirado(String token) {
        return extrairClaim(token, Claims::getExpiration).before(new Date());
    }
}
