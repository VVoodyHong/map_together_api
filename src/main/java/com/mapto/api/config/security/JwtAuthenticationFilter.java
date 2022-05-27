package com.mapto.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapto.api.app.user.service.UserDetailsServiceImpl;
import com.mapto.api.common.model.ApiResponse;
import com.mapto.api.common.model.StatusCode;
import com.mapto.api.common.util.CheckUtil;
import com.mapto.api.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = JwtUtil.getJwtFromRequest(request);
            if(jwt != null) {
                JwtValidation jwtValidation = jwtProvider.validateToken(jwt);
                if (jwtValidation.isSuccess()) {
                    Long userIdx = jwtProvider.getUserIdxFromJwt(jwt);
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByIdx(userIdx);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                "",
                                userDetails.getAuthorities()
                        );
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } else {
                        log.error("Could not find user by index");
                        returnError(response, StatusCode.CODE_604);
                        return;
                    }
                } else {
                    log.error("Failed to validate jwt");
                    returnError(response, jwtValidation.getCode());
                    return;
                }
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
            returnError(response, StatusCode.CODE_652);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void returnError(HttpServletResponse response, StatusCode errorCode) throws IOException{
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), new ApiResponse(errorCode));
    }
}
