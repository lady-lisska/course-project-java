package com.lisska.onboarding.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Секретный ключ для подписи (должен быть длинным и в формате Base64).
    // В реальных проектах его прячут в application.yml
    private final String jwtSecret = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

    // Время жизни токена (24 часа в миллисекундах)
    private final long jwtExpirationDate = 86400000;

    // Метод 1: Генерация токена
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    // Метод 2: Достаем email (username) из токена
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // Метод 3: Проверка валидности токена
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Недействительный JWT токен: " + e.getMessage());
        }
        return false;
    }

    // Превращаем строку в криптографический ключ
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}