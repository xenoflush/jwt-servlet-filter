package com.sparta.jwtservletfilter.service;

import com.sparta.jwtservletfilter.dto.RequestDto;
import com.sparta.jwtservletfilter.entity.User;
import com.sparta.jwtservletfilter.repository.UserRepository;
import com.sparta.jwtservletfilter.util.JwtUtil;
import com.sparta.jwtservletfilter.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * - 사용자 저장
 * - 로그인 처리 및 JWT 생성
 *
 * @author JaeHwan Kim
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;  // 사용자 정보를 저장 및 조회하기 위한 리포지토리
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 및 검증을 위한 클래스
    private final JwtUtil jwtUtil; // JWT 토큰 생성 및 검증을 위한 유틸리티 클래스

    /**
     * 사용자 정보를 저장합니다.
     *
     * @param user 저장할 사용자 엔티티
     * @return 저장된 사용자 엔티티
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 사용자 로그인을 처리하고 JWT 토큰을 생성합니다.
     *
     * @param requestDto 로그인 요청 정보 (사용자 이름 및 비밀번호 포함)
     * @return 로그인 결과를 담은 ResponseEntity 객체
     */
    public ResponseEntity<Object> login(RequestDto requestDto) {
        String username = requestDto.getUsername(); // 요청에서 사용자 이름을 추출
        String password = requestDto.getPassword(); // 요청에서 비밀번호를 추출

        // 사용자 확인
        // 사용자 이름으로 사용자 정보를 조회하고, 없으면 예외를 던집니다.
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        // 입력된 비밀번호와 데이터베이스에 저장된 비밀번호를 비교합니다.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 헤더에 저장
        // JWT를 생성하여 사용자의 이름과 역할을 포함시킵니다.
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // 응답 헤더에 JWT 토큰을 추가합니다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);

        // ResponseEntity를 생성하여 상태 코드를 201(CREATED)로 설정하고, 헤더에 JWT를 포함하여 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).build();
    }
}