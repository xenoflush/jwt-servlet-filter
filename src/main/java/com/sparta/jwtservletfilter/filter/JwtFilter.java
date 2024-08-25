package com.sparta.jwtservletfilter.filter;

import com.sparta.jwtservletfilter.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JwtFilter는 HTTP 요청을 필터링하여 JWT 토큰의 유효성을 검증합니다.
 * - 필터 초기화 및 종료 시 로직을 추가할 수 있습니다.
 * - 모든 요청에 대해 JWT 인증을 수행하며, JWT가 없거나 유효하지 않으면 에러를 반환합니다.
 *
 * @author JaeHwan Kim
 * @version 1.0
 * @since 1.0
 */
@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 로직이 필요하다면 여기에 추가합니다.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String username = null;
        String jwt = null;

        // 헤더에서 Authorization 토큰을 가져옵니다.
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // 허용할 URL 패턴 정의
        if (requestURI.equals("/api/login")) {
            // 인증이 필요하지 않은 URL은 바로 다음 필터로 전달
            chain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        // JWT 토큰에서 'Bearer ' 부분을 제거하고 토큰만 추출합니다.
        String token = authorizationHeader.substring(7);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && jwtUtil.validateToken(jwt)) {
            // JWT가 유효한 경우
            log.info("JWT TOKEN VALID");
            chain.doFilter(request, response);
        } else {
            // 권한이 없으면 403 Forbidden 응답
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("{\"error\": \"Unauthorized\"}");
        }
    }

    @Override
    public void destroy() {
        // 필터 종료 로직이 필요하다면 여기에 추가합니다.
    }

}
