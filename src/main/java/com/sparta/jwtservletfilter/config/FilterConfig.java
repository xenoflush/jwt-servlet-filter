package com.sparta.jwtservletfilter.config;

import com.sparta.jwtservletfilter.filter.JwtFilter;
import com.sparta.jwtservletfilter.util.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * - Spring Boot 애플리케이션의 필터 구성을 설정하는 클래스입니다.
 * - 이 클래스는 JWT 필터를 등록하고 설정하여, HTTP 요청을 필터링할 수 있도록 합니다.
 *  * @author JaeHwan Kim
 *  * @version 1.0
 *  * @since 1.0
 */
@Configuration
public class FilterConfig {

    /**
     * JWT 필터를 등록하고 필터 설정을 구성하는 빈을 생성합니다.
     *
     * @param jwtUtil JWT 유틸리티 클래스를 주입받아 필터에 전달합니다.
     * @return 필터 등록 및 설정을 위한 FilterRegistrationBean 객체
     */
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        // FilterRegistrationBean을 생성하여 필터 등록을 위한 설정을 구성합니다.
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();

        // JwtFilter를 생성하여 jwtUtil을 주입합니다.
        registrationBean.setFilter(new JwtFilter(jwtUtil));

        // 필터가 적용될 URL 패턴을 지정합니다.
        // 현재는 모든 URL 패턴에 대해 필터를 적용합니다.
        registrationBean.addUrlPatterns("/*");

        return registrationBean; // 설정된 필터 등록 빈을 반환합니다.
    }
}