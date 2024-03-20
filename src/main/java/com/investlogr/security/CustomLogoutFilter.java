package com.investlogr.security;

import com.investlogr.domain.dao.RefreshTokenRepository;
import com.investlogr.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        //path and method verify
        String requestUri = req.getRequestURI();
        if(!requestUri.matches("^\\/logout")){
            chain.doFilter(req,res);
            return;
        }
        String requestMethod = req.getMethod();
        if(!requestMethod.equals("POST")){
            chain.doFilter(req,res);
            return;
        }

        //get Refresh token
        String refresh = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
            }
        }

        //refresh null check
        if(refresh == null){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //refresh category check
        String category = jwtUtil.getCategory(refresh);
        if(!category.equals("refresh")){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //DB check
        Boolean isExist = refreshTokenRepository.existsByRefresh(refresh);
        if(!isExist){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //로그아웃 진행
        //refresh db 제거
        refreshTokenRepository.deleteByRefresh(refresh);

        //refresh cookie null
        Cookie cookie = new Cookie("refresh",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        res.addCookie(cookie);
        res.setStatus(HttpServletResponse.SC_OK);
    }
}
