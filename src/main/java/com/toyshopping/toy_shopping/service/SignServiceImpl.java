package com.toyshopping.toy_shopping.service;

import com.toyshopping.toy_shopping.common.CommonResponse;
import com.toyshopping.toy_shopping.config.security.JwtTokenProvider;
import com.toyshopping.toy_shopping.data.dto.JwtTokenDto;
import com.toyshopping.toy_shopping.data.dto.SignUpDto;
import com.toyshopping.toy_shopping.data.dto.SignUpResultDto;
import com.toyshopping.toy_shopping.data.dto.UsersDto;
import com.toyshopping.toy_shopping.data.entity.Users;
import com.toyshopping.toy_shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SignServiceImpl implements SignService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public UsersDto signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUid(signUpDto.getUid())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return UsersDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
        /*LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        Users users;
        if (role.equalsIgnoreCase("admin")) {
            users = Users.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .phone(phone)
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            users = Users.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .phone(phone)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        Users saveUsers = userRepository.save(users);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if(!saveUsers.getName().isEmpty()){
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpresult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }*/

    }

    @Transactional
    @Override
    public JwtTokenDto signIn(String id, String password) {
        /*LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByUid(id);
        LOGGER.info("[getSignInResult] id : {} ", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
                .build();

        LOGGER.info("[getSignInResult] SignInResult 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;*/

        // id + password 기반 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtToken = jwtTokenProvider.generateToken(authentication);

        LOGGER.info("jwtToken:"+jwtToken);

        return jwtToken;
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }



}
