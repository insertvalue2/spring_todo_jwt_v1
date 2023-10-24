package com.tencoding.todo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

// @Configuration 어노테이션은 해당 클래스가 스프링 프레임워크의 
// 설정 정보를 담고 있는 클래스임을 나타냅니다.
// @Component 상속 받고 있음
@Slf4j
@Configuration  
public class FilterConfig {
	
	// 우리가 정의한 JWT 관련된 필터 동작 객체를 생성자 의존 주입을 받는다. 
	@Autowired
    private JweRequestFilter jweRequestFilter;
	
	// FilterRegistrationBean을 생성하여 필터를 등록하는 메서드를 정의합니다.
    @Bean
    public FilterRegistrationBean<JweRequestFilter> loggingFilter() {
    	log.error("FilterConfig 초기화 확인 - 서버 구동시 호출 확인");
    	//System.out.println("스프링부트 구동시 초기화 확인");
    	
    	// 기존의 서블릿 기반 애플리케이션에서는 web.xml 파일을 사용하여
    	// 필터를 등록하고 설정했지만, 스프링 부트에서는 XML 기반 대신 
    	// FilterRegistrationBean 과 같은 클래스를 제공해 
    	// 커스텀한 필터를 등록할 수 있다
        FilterRegistrationBean<JweRequestFilter> registrationBean 
        	= new FilterRegistrationBean<>();
        // 필터 등록 
        registrationBean.setFilter(jweRequestFilter);
        // 이 필터를 어떤 URL 패턴에 적용할 것인지 설정합니다.
        registrationBean.addUrlPatterns("/todos/*");
        // setOrder(int order): 여러 개의 필터가 동시에 적용되는 경우,
        // 그 실행 순서를 결정합니다. 1,2,3 작을 수록 우선 순위 
        return registrationBean;
    }
}
