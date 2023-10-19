package com.tencoding.todo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tencoding.todo.utils.JwtUtil;


@Component
public class JweRequestFilter implements Filter {
 
	// 성능 문제 방지: System.out.println은 내부적으로 동기화가 이루어져 성능 저하의 원인이 될 수 있음.
	// 로깅 프레임워크는 비동기 방식으로 작동하여 성능 저하 문제를 완화
	// Lombok 사용한다면 @Slf4j 어노테이션을 활용 (보일러플레이트 코드를 줄여 준다.)
    private static final Logger logger = LogManager.getLogger(JweRequestFilter.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	    
	    logger.info("JWT Filter 동작 확인 - 1");
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
	    String authHeader = httpServletRequest.getHeader("Authorization");
	    logger.info("authHeader 확인 : {}", authHeader);

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        sendUnauthorizedError(response);
	        return; 
	    } 

	    String jwtToken = authHeader.substring(7);
	    logger.info("JWT Filter 동작 확인 - 2");
	    
	     if (!jwtUtil.validateToken(jwtToken)) {
	         logger.warn("JWT Filter 동작 확인 - 3: Invalid Token");
	         sendUnauthorizedError(response);
	         return; 
	     }
	    
	     chain.doFilter(request, response); 
	}

	private void sendUnauthorizedError(ServletResponse response) throws IOException {
	   HttpServletResponse httpResponse = (HttpServletResponse) response; 
	   httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token"); 
	}
	
	// 다음 작업 -> 서블릿 컨테이너에 필터를 등록해여 동작하도록 설정 하자 !! 
	
}
