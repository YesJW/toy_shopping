package com.toyshopping.toy_shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyshopping.toy_shopping.config.JwtExceptionResponse;
import com.toyshopping.toy_shopping.config.security.JwtTokenProvider;
import com.toyshopping.toy_shopping.data.dto.JwtTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class RefreshTokenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/reissue")
    public ResponseEntity<Object> reissueToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
            token = token.substring(7);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            String uid = authentication.getName();
            System.out.println("Name = " + uid);

            String refreshToken = (String) redisTemplate.opsForValue().get("RefreshT:" + uid);
            if (jwtTokenProvider.validateToken(refreshToken)) {
                JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(authentication);
                redisTemplate.opsForValue().set("RefreshT:" + uid, jwtTokenDto.getRefreshToken());
                return ResponseEntity.ok().body(jwtTokenDto);
            }
            else{
                response.setStatus(401);
                response.setContentType("application/json; charset=UTF-8");

                JwtExceptionResponse jwtExceptionResponse = JwtExceptionResponse.builder()
                        .httpstatus(HttpStatus.UNAUTHORIZED)
                        .throwableMessage("리프레시 토큰 만료")
                        .build();
                String responseToJson = objectMapper.writeValueAsString(jwtExceptionResponse);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseToJson);
            }

        }
        return null;
    }
}
