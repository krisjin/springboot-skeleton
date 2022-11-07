package org.yeyu.springboot.gateway.provider.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface IToken {

    /**
     * 创建token
     *
     * @param map
     * @return
     */
    String generateToken(Map<String, String> map);

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    Claims parseToken(String token);


}
