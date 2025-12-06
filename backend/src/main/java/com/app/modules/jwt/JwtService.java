package com.app.modules.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.app.modules.jwt.records.JwtPayload;
import com.app.modules.users.enums.UserRoleEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${JWT_SECRET_KEY}")
  private String secretKey;

  @Value("${JWT_EXPIRY_TIME_IN_MILLISECONDS}")
  private Long expiryTimeInMilliseconds;

  private SecretKey key;

  @PostConstruct
  public void init() {
    key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(JwtPayload jwtPayload) {
    Date expiryDate = new Date(System.currentTimeMillis() + expiryTimeInMilliseconds);
    return Jwts.builder()
        .claim("userId", jwtPayload.userId())
        .claim("role", jwtPayload.role())
        .expiration(expiryDate)
        .issuedAt((new Date()))
        .signWith(this.key)
        .compact();
  }

  public JwtPayload validateAndExtractToken(String token) {
    if (token == null || token.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is required");
    }
    try {
      Claims claims = Jwts.parser().verifyWith(this.key).build().parseSignedClaims(token).getPayload();
      Long userId = claims.get("userId", Long.class);
      String roleValue = claims.get("role", String.class);
      UserRoleEnum role = UserRoleEnum.fromValue(roleValue);
      return new JwtPayload(userId, role);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
  }
}
