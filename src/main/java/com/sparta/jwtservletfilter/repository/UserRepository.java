package com.sparta.jwtservletfilter.repository;

import com.sparta.jwtservletfilter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * @author JaeHwan Kim
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 사용자 이름으로 사용자를 조회합니다.
     *
     * @param username 사용자의 이름
     * @return 사용자 정보를 감싼 Optional 객체 (존재하면 User 객체, 그렇지 않으면 빈 Optional)
     */
    Optional<User> findByUsername(String username);
}