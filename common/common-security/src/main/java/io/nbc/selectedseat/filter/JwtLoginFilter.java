package io.nbc.selectedseat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nbc.selectedseat.domain.member.model.MemberRole;
import io.nbc.selectedseat.dto.LoginRequestDTO;
import io.nbc.selectedseat.dto.LoginResponseDTO;
import io.nbc.selectedseat.dto.ResponseDTO;
import io.nbc.selectedseat.jwt.JwtUtil;
import io.nbc.selectedseat.security.userdetail.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtLoginFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/v1/members/login");
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDTO requestDto = new ObjectMapper()
                .readValue(request.getInputStream(), LoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.email(),
                    requestDto.password(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        Long memberId = ((UserDetailsImpl) authResult.getPrincipal()).getMember().getMember_id();
        MemberRole role = ((UserDetailsImpl) authResult.getPrincipal()).getMember()
            .getMember_role();

        String jsonResponse = new ObjectMapper().writeValueAsString(
            ResponseDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(new LoginResponseDTO(memberId))
                .build()
        );

        String token = jwtUtil.createToken(memberId, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
    }
}
