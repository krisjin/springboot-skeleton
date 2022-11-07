package org.yeyu.springboot.gateway.provider.service.impl;

import com.lucas.gateway.enums.JwtSecret;
import com.lucas.gateway.jwt.JwtUtils;
import com.lucas.gateway.provider.service.IToken;
import io.jsonwebtoken.Claims;

import java.util.Map;

public class ColumbusToken implements IToken {
    /**
     * 创建token
     *
     * @param map
     * @return
     */
    @Override
    public String generateToken(Map<String, String> map) {
        return null;
    }

    /**
     * ji
     *
     * @param token
     * @return
     */
    @Override
    public Claims parseToken(String token) {
        Claims claims = JwtUtils.parseJwt(token, JwtSecret.COLUMBUS.getSecret());
        return claims;
    }
}
