package com.sparta.jwtservletfilter.controller;

import com.sparta.jwtservletfilter.dto.RequestDto;
import com.sparta.jwtservletfilter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RequestDto requestDto) {

        String token = userService.login(requestDto);

        // 응답 헤더에 JWT 토큰을 추가합니다.
        // ResponseEntity를 생성하여 상태 코드를 201(CREATED)로 설정하고, 헤더에 JWT를 포함하여 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).header("Authorization", token).build();
    }
}
