package com.sparta.jwtservletfilter.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    // @RequiresRole("ADMIN") <- AOP 활용시 사용하시면 됩니다.
    @GetMapping("/get")
    public String getAdminInfo(HttpServletRequest request) {
        log.info("어드민 페이지 호출");
        // 토큰 검증
        return "어드민 페이지 리소스가 허가되었습니다.";
    }
}
