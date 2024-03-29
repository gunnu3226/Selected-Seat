package io.nbc.selectedseat.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nbc.selectedseat.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException)
        throws IOException {
        String jsonResponse = new ObjectMapper().writeValueAsString(
            ResponseDTO.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("유효하지 않은 토큰 접근"));
        log.error("유효하지 않은 토큰 접근");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonResponse);
    }
}
