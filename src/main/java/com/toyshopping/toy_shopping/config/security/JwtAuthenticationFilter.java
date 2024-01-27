package com.toyshopping.toy_shopping.config.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    /* private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
     private final JwtTokenProvider jwtTokenProvider;

     public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
         this.jwtTokenProvider = jwtTokenProvider;
     }

     @Override
     protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String token = jwtTokenProvider.resolveToken(servletRequest);
         LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

         LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
         try {
             if (token != null && jwtTokenProvider.validateToken(token)) {
                 Authentication authentication = jwtTokenProvider.getAuthentication(token);
                 response.setHeader("X-AUTH-TOKEN", token);
                 SecurityContextHolder.getContext().setAuthentication(authentication);
                 LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
             }
         } catch (JwtException exception) {
             LOGGER.error("[JwtAuthenticationFilter] JWT parsing error : {}", exception.getMessage());
             SecurityContextHolder.clearContext();
         }

         filterChain.doFilter(servletRequest, response);
     }*/
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(8);
        }
        return null;
    }
}
