//package com.example.family_budget.util;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private static final String SECRET_KEY = "ваш_секретный_ключ"; // замените на свой секрет
//
//    // Генерация токена
//    public String generateToken(Long userId) {
//        return Jwts.builder()
//                .setSubject(String.valueOf(userId))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 час
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    // Проверка и парсинг токена
//    public Long validateTokenAndGetUserId(String token) {
//        return Long.parseLong(Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject());
//    }
//}
