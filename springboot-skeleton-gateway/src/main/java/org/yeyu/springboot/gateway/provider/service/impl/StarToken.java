package org.yeyu.springboot.gateway.provider.service.impl;

import com.lucas.gateway.enums.JwtSecret;
import com.lucas.gateway.jwt.JwtUtils;
import com.lucas.gateway.provider.service.IToken;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StarToken implements IToken {

    /**
     * 创建token
     *
     * @param map
     * @return
     */
    @Override
    public String generateToken(Map<String, String> map) {
//       JwtUtils

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
