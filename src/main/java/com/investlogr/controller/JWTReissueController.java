package com.investlogr.controller;

import com.investlogr.domain.dao.RefreshTokenRepository;
import com.investlogr.domain.entity.RefreshToken;
import com.investlogr.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JWTReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JWTReissueController(JWTUtil jwtUtil,RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("refresh"))
                refresh = cookie.getValue();
        }

        if (refresh == null){
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e){
            //response status code
            return new ResponseEntity<>("refresh token expired",HttpStatus.BAD_REQUEST);
        }

        //refresh token check
        String category = jwtUtil.getCategory(refresh);

        if(!category.equals("refresh")){
            return new ResponseEntity<>("invalid refresh token",HttpStatus.BAD_REQUEST);
        }

        //get information
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new access token
        String newAccess = jwtUtil.createJwt("access",username,role,600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //refresh token 저장, 기존 refresh token 삭제 후 새 refresh token 저장
        refreshTokenRepository.deleteByRefresh(refresh);
        addRefreshToken(username,newRefresh,86400000L);

        //response
        response.setHeader("access",newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshToken(String username, String refresh, Long expiredMs){
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(date.toString());

        refreshTokenRepository.save(refreshToken);
    }
}
