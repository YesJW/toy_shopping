package com.toyshopping.toy_shopping.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyshopping.toy_shopping.config.JwtExceptionResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, exception);

        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable throwable) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        JwtExceptionResponse jwtExceptionResponse = JwtExceptionResponse.builder()
                .httpstatus(HttpStatus.UNAUTHORIZED)
                .throwableMessage(throwable.getMessage())
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(jwtExceptionResponse));

    }
}
