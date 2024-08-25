package com.sparta.jwtservletfilter.aop;

import com.sparta.jwtservletfilter.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * AOP 어드바이스 클래스 - 메서드 호출 전 JWT 권한 체크
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final JwtUtil jwtUtil; // JWT 유틸리티 클래스, 토큰 처리 및 권한 확인 기능 제공
    private final HttpServletRequest request; // HTTP 요청 객체, 헤더에서 JWT 토큰을 추출하기 위해 사용

    /**
     * 메서드 실행 전에 권한 체크를 수행하는 AOP 어드바이스
     * @param requiresRole: 메서드에 적용된 @RequiresRole 애노테이션
     */
    @Before("@annotation(requiresRole)") // @RequiresRole 애노테이션이 붙은 메서드 실행 전에 이 메서드가 호출됨
    public void checkRole(RequiresRole requiresRole) {
        String requiredRole = requiresRole.value(); // 애노테이션에서 요구되는 역할(권한)을 추출

        // Authorization 헤더에서 JWT 토큰을 추출
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면, 권한이 없다는 예외를 던짐
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "헤더에 정보가 없거나 유효하지 않습니다.");
        }

        // "Bearer " 접두사를 제거하여 실제 토큰 값을 추출
        String token = authorizationHeader.substring(7);

        // JWT 토큰에서 필요한 권한이 있는지 확인
        if (!jwtUtil.hasRole(token, requiredRole)) {
            // 필요한 역할이 없는 경우, 권한이 없다는 예외를 던짐
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 권한은 유효하지 않습니다");
        }
    }
}