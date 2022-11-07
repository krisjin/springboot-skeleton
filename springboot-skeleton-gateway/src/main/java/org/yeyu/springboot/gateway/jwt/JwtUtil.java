package org.yeyu.springboot.gateway.jwt;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.yeyu.springboot.gateway.common.Constants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 */
@Slf4j
public class JwtUtil {

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey(String key) {
        byte[] encodedKey = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, Constants.JWT_HS256);
    }


    /**
     * 创建jwt
     * todo 区分哥伦布、星职场创建token
     */
    public static String createJWT(Map<String, Object> paramMap, Long expTime) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


//        JwtModel jwtModel = new JwtModel(id, 1664672564, "https://columbus-app-api-test.lucasgchr.com/");


//        Map<String, Object> claims = JSON.parseObject(JSON.toJSONString(jwtModel), new TypeReference<Map<String, Object>>() {
//        });
//
//        Map<String, Object> claimsMap = Maps.newHashMap();

//        claimsMap.put("user_id", id);
//        claimsMap.put("exp", ttlMillis);
//        claimsMap.put("domain", "https://columbus-app-api-test.lucasgchr.com");

        SecretKey secretKey = generalKey(JwtSecret.STAR.getSecret());

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
        // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
        // iat: jwt的签发时间
        // issuer：jwt签发人
        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
        // 设置签名使用的签名算法和签名使用的秘钥

        JwtBuilder builder = Jwts.builder().setClaims(paramMap)
//                .setId(String.valueOf(id))
                .setIssuedAt(new Date())
//                .setIssuer(issuer)
//                .setSubject(subject)
                .signWith(signatureAlgorithm, secretKey);

        // 设置过期时间
        if (expTime >= 0) {
            long expMillis = System.currentTimeMillis() + expTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     */
    public static Claims parseJWT(String jwt) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey(JwtSecret.STAR.getSecret());
        log.info("jwt:{},key:{}", jwt, key);

        //得到DefaultJwtParser, 设置签名的秘钥, 设置需要解析的jwt
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 检查token
     *
     * @return
     */
    public static boolean checkToken(String jwtToken, ObjectMapper objectMapper) {
        Claims claims = JwtUtil.parseJWT(jwtToken);
        JwtModel jwtModel = JSON.parseObject(JSON.toJSONString(claims), JwtModel.class);
        /*
            TODO 对jwt里面的用户信息做判断
         */

        /*
            获取token的过期时间，和当前时间作比较，如果小于当前时间，则token过期
         */
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date expiration = claims.getExpiration();
        log.info("======== token的过期时间：" + df.format(expiration));
        return true;
    }

    public static void main(String[] args) {
        String t = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMjM5NSIsImV4cCI6MTY2OTg3MjkzNywiZG9tYWluIjoiaHR0cHM6XC9cL3NhYXMtYXBwLWFwaS5sdWNhc2djaHIuY29tXC8ifQ.R-LasUJgjy8ZtHHVM9pkCBDWphk41yGi-tER-XVe7Us";
//        String t = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMjEiLCJleHAiOjE2NjY4NTc0ODMsImRvbWFpbiI6Imh0dHBzOlwvXC9zYWFzLWFwcC1hcGktdGVzdC5sdWNhc2djaHIuY29tXC8ifQ.WLEzIB3tOPM4n_StF2puy4mz3MGKiTjx0ghvHfbP95w";
        Claims d = parseJWT(t);
        System.out.println(d);

//        Map param = new HashMap();
////        param.put("exp", 1666857483121L);
//        param.put("domain", "https://saas-app-api-test.lucasgchr.com/}");
//        param.put("user_id", "787");
////
////        String newToken ="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNzg3IiwiZG9tYWluIjoiaHR0cHM6Ly9zYWFzLWFwcC1hcGktdGVzdC5sdWNhc2djaHIuY29tL30iLCJleHAiOjMzMzI0MTYxNDcsImlhdCI6MTY2NTU1ODY2NH0.T4bmcScGB-8iZ-gM4XWZfRXbqhCCNpVmeZAyq19TeVI";
//
//        String token = createJWT(param, 43253453L);
//
//        System.err.println("###"+token);
//
//        d = parseJWT(token);
//        System.out.println(d);

    }

}
