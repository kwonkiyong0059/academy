package com.sparta.academy.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.academy.admin.dto.AdminResponseDto;
import com.sparta.academy.admin.dto.LoginRequestDto;
import com.sparta.academy.enums.UserRoleEnum;
import com.sparta.academy.exception.RestApiException;
import com.sparta.academy.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        response.setContentType("application/json");

        // JSON 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        AdminResponseDto responseDto = new AdminResponseDto(((UserDetailsImpl) authResult.getPrincipal()).getUser());
        String jsonString = objectMapper.writeValueAsString(responseDto);

        // JSON 응답 작성
        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // JSON 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        RestApiException restApiException = new RestApiException("Failed to login.", HttpServletResponse.SC_UNAUTHORIZED);
        String jsonString = objectMapper.writeValueAsString(restApiException);

        // JSON 응답 작성
        PrintWriter out = response.getWriter();
        out.print(jsonString);
        out.flush();
    }
}
