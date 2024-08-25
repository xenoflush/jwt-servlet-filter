package com.sparta.jwtservletfilter.controller;

import com.sparta.jwtservletfilter.aop.RequiresRole;
import com.sparta.jwtservletfilter.dto.RequestDto;
import com.sparta.jwtservletfilter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @RequiresRole("USER")
    public String getUserInfo(HttpServletRequest request) {
        return "유저 페이지 리소스가 허가되었습니다.";
    }

    @GetMapping("/admin")
    @RequiresRole("ADMIN")
    public String getAdminInfo(HttpServletRequest request) {
        return "어드민 페이지 리소스가 허가되었습니다.";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RequestDto requestDto, HttpServletRequest request) {
        return userService.login(requestDto);
    }

}
