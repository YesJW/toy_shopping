package com.toyshopping.toy_shopping.controller;

import com.toyshopping.toy_shopping.config.security.JwtTokenProvider;
import com.toyshopping.toy_shopping.data.dto.JwtTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class RefrshTokenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissueToken(HttpServletRequest request) {
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
        }
        return null;
    }
}
