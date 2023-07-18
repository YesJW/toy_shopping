package com.toyshopping.toy_shopping.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyshopping.toy_shopping.data.dto.EntryPointErrorResponse;
import net.bytebuddy.build.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        if (isAjaxRequest(request)) {
//            // Ajax 요청인 경우 처리 로직
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요한 페이지입니다."); // 에러 응답 반환
//        } else {
//
//            // 일반적인 요청인 경우 처리 로직
//            String redirectUrl = "/sign-api/login"; // 로그인 페이지로 리다이렉트할 URL
//            String confirmMessage = "로그인이 필요한 페이지입니다. 로그인 페이지로 이동하시겠습니까?"; // 사용자에게 보여줄 확인 메시지
//
//            // JavaScript 코드를 작성하여 확인 대화상자를 표시하고 사용자의 응답에 따라 페이지 이동을 처리
//            String script = "<script>" +
//                    "var confirmRedirect = confirm('" + confirmMessage + "');" +
//                    "if (confirmRedirect) { window.location.href = '" + redirectUrl + "'; }" +
//                    "</script>";
//
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write(script);
//        }
//    }
//
//    private boolean isAjaxRequest(HttpServletRequest request) {
//        String header = request.getHeader("X-Requested-With");
//        return "XMLHttpRequest".equals(header);
//    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("[Commence] 인증 실패로 response. sendError 발생");

        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");

        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
    }
}