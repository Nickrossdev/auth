package com.nickross.auth.config;

import com.nickross.auth.domain.auth.models.account.dto.AccountCurrentData;
import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account.AccountService;
import com.nickross.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AccountService accountService;
    @Value("${cookie.name}")
    private String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromCookies(request);

        if (token != null) {
            try {
                UUID userId = jwtUtil.getSubject(token);
                Account account = accountService.findAccountAllRoles(userId);
                AccountCurrentData accountCurrentData = new AccountCurrentData(account);
                List<SimpleGrantedAuthority> authorities = accountCurrentData.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
                Authentication authentication = new UsernamePasswordAuthenticationToken(accountCurrentData, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                request.setAttribute("jwt_exception", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}