package com.tencoding.todo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	private final long EXPIRATION_TIME = 86400000L; // 토큰 유효 시간: 1일 (밀리초 단위로 계산된 값)

	// 클레임(Claim)은 JWT 내에서 데이터를 표현하고 전달하는 방식입니다.
	// 여러 가지 유용한 정보를 포함하여 필요한 데이터를 전송할 수 있습니다. 예를 들어, 사용자 식별정보나 권한 정보 등을 클레임에 담아서
	// 토큰에 포함시켜 인증 및 권한 부여 과정에서 활용할 수 있습니다.
	public String generateToken(String userEmail, Integer userId) {
		return Jwts.builder()
				.claim("userEmail", userEmail) // 토큰에 userEmail 클레임 추가
				.claim("userId", userId) // 토큰에 userId 클레임 추가
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 유효 시간 포함 
				.signWith(key) // 시크릿 키 값 포함  
				.compact(); // String 으로 변환 후 리턴
	}

	/**
	 * JWT 토큰 유효성 검사
	 * 
	 * @param token 클라이언트가 보낸 JWT 토큰 문자열
	 * @return 토큰의 유효성 (true: 유효, false: 무효). 서명 검사와 만료 시간 검사를 모두 포함.
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true; // 예외 없이 파싱 완료되면, 토큰은 유효함.
		} catch (Exception e) {
			return false; // 어떤 예외든 발생하면, 토큰은 무효함.
		}
	}

	// JWT 토큰정보에서 userId 값 추출 함수
	public String getUserIdFromToken(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);

		return claimsJws.getBody().get("userId", String.class); // 토큰에서 userId 값을 추출하여 반환
	}

	// JWT 토큰정보에서 사용자 email 값 추출 함수
	public String getEmailFromToken(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

		return claimsJws.getBody().get("userEmail", String.class);
	}

}
