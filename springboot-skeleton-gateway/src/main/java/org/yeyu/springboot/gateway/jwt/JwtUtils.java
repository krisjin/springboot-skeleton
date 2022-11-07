package org.yeyu.springboot.gateway.jwt;

import com.lucas.gateway.common.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kris
 * @date 2022/9/16
 */
@Slf4j
public class JwtUtils {

    /**
     * 检查token
     *
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        Claims claims = JwtUtil.parseJWT(jwtToken);

        // TODO 对jwt里面的用户信息做判断


        // 获取token的过期时间，和当前时间作比较，如果小于当前时间，则token过期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date expiration = claims.getExpiration();
        log.info("======== token的过期时间：" + df.format(expiration));
        return true;
    }


    /**
     * 解析Jwt
     */
    public static Claims parseJwt(String jwt, String secret) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey(secret);

        //得到DefaultJwtParser, 设置签名的秘钥, 设置需要解析的jwt
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey(String key) {
        byte[] encodedKey = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, Constants.JWT_HS256);
    }


}
