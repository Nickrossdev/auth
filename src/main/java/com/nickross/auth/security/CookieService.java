package com.nickross.auth.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CookieService {
    @Value("${cookie.name}")
    private String cookieName;
    @Value("${cookie.expiration}")
    private Integer cookieExpiration;

    public void createCookie() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (response!=null) {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }
    }

    public void createCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (response!=null) {
            Cookie cookie = new Cookie(cookieName, token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(cookieExpiration);

            response.addCookie(cookie);
        }
    }

}
