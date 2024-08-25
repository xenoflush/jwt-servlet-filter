package com.sparta.jwtservletfilter.init;


import com.sparta.jwtservletfilter.entity.User;
import com.sparta.jwtservletfilter.entity.UserRoleEnum;
import com.sparta.jwtservletfilter.service.UserService;
import com.sparta.jwtservletfilter.util.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원정보 데이터 초기화
 *
 * @author JaeHwan Kim
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class InitData {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @PostConstruct
  @Transactional
  public void init() {
    List<User> productList =
        List.of(new User("홍길동",passwordEncoder.encode("1234"),"hong@sparta.com", UserRoleEnum.ADMIN),
                new User("이순신",passwordEncoder.encode("1234"),"lee@sparta.com",UserRoleEnum.USER),
                new User("신시임당",passwordEncoder.encode("1234"),"shin@sparta.com",UserRoleEnum.ADMIN));
    for (User product : productList) {
      userService.save(product);
    }
  }
}
