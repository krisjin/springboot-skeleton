package org.yeyu.springboot.gateway.provider.service.impl;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.yeyu.springboot.gateway.enums.JwtSecret;
import org.yeyu.springboot.gateway.jwt.JwtUtils;
import org.yeyu.springboot.gateway.provider.service.IToken;

import java.util.Map;

@Component
public class StarToken implements IToken {

    /**
     * @param map
     * @return
     */
    @Override
    public String generateToken(Map<String, String> map) {
        return null;
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Claims parseToken(String token) {
        Claims claims = JwtUtils.parseJwt(token, JwtSecret.STAR.getSecret());
        return claims;
    }
}
