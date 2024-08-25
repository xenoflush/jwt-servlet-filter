package com.sparta.jwtservletfilter.util;

import at.favre.lib.crypto.bcrypt.BCrypt; // BCrypt 라이브러리 임포트
import org.springframework.stereotype.Component;

/**
 * 비밀번호 인코더 클래스
 * - BCrypt를 사용하여 비밀번호를 암호화하고, 암호화된 비밀번호와 원문 비밀번호를 비교하는 기능을 제공합니다.
 *
 * @author JaeHwan Kim
 * @version 1.0
 * @since 1.0
 */
@Component
public class PasswordEncoder {

    /**
     * 원문 비밀번호를 BCrypt 알고리즘을 사용하여 암호화합니다.
     * @param rawPassword 원문 비밀번호
     * @return 암호화된 비밀번호 문자열
     */
    public String encode(String rawPassword) {
        // BCrypt의 기본 설정을 사용하여 비밀번호를 해시화
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 원문 비밀번호와 암호화된 비밀번호를 비교하여 일치 여부를 확인합니다.
     * @param rawPassword 원문 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 비밀번호 일치 여부 (true: 일치, false: 불일치)
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        // BCrypt를 사용하여 원문 비밀번호와 암호화된 비밀번호를 비교
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified; // 비교 결과 반환
    }
}