package com.tencoding.todo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component  // 이 클래스를 Spring의 Bean으로 등록하여 다른 클래스에서 자동 주입(Autowired)을 사용할 수 있도록 함
public class JwtUtil {

    // HS512 알고리즘에 맞는 비밀 키 생성. HS512 알고리즘은 HMAC(Hash-based Message Authentication Code)를 SHA-512 해시 알고리즘으로 적용한 것.
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 토큰 유효 시간: 1일 (밀리초 단위로 계산된 값). 이 시간 이후로는 토큰이 만료됨.
    private final long EXPIRATION_TIME = 86400000L; 

    /**
     * 사용자의 이메일 주소를 이용하여 JWT 토큰 생성
     * @param userEmail 사용자 이메일. 이 값을 subject로 설정하여, 나중에 토큰에서 사용자 정보를 추출할 수 있게 함.
     * @return 생성된 JWT 토큰 문자열. 클라이언트에게 전달되어 인증 정보로 활용됨.
     */
    public String generateToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(key) // 위에서 생성한 비밀키로 JWT 서명. 서명을 통해 데이터의 무결성을 보장함.
                .compact(); // 완성된 JWT 문자열을 반환함. compact() 호출 전까지는 builder 패턴으로 메서드 체이닝 가능.
    }

    /**
     * JWT 토큰에서 사용자 이메일 추출
     * @param token 클라이언트가 보낸 JWT 토큰 문자열
     * @return 추출된 사용자의 이메일 주소(subject)
     */
    public String getEmailFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);  // 파싱 과정에서 서명 검사도 수행됨.

        return claimsJws.getBody().getSubject();  // Claims 객체에서 subject를 반환함.
    }

    /**
     * JWT 토큰 유효성 검사
     * @param token 클라이언트가 보낸 JWT 토큰 문자열
     * @return 토큰의 유효성 (true: 유효, false: 무효). 서명 검사와 만료 시간 검사를 모두 포함.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);  // 파싱 과정에서 예외 발생시 토큰이 무효한 것으로 간주.
            return true;  // 예외 없이 파싱 완료되면, 토큰은 유효함.
        } catch (Exception e) {
            return false;  // 어떤 예외든 발생하면, 토큰은 무효함.
        }
    }
}
