package org.yeyu.springboot.gateway.provider.service;

import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yeyu.springboot.gateway.provider.service.impl.StarToken;

import javax.annotation.Resource;
import java.util.List;

/**
 * 根据appId，动态适配获对应的token解析逻辑，从token中解析出userId
 *
 * @author kris
 * @date 2022/9/16
 */
@Component
@Slf4j
public class TokenAdapter {

    @Resource
    private StarToken starToken;

    public List<String> getUserId(String appId, String token) {

        Claims claims = starToken.parseToken(token);
        String userId = String.valueOf(claims.get("user_id"));
        List<String> userIdList = Lists.newArrayList(userId);

        return userIdList;
    }


    public List<String> getStaffId(String appId, String token) {

        Claims claims = starToken.parseToken(token);
        String staffId = String.valueOf(claims.get("staff_id"));
        List<String> staffIdList = Lists.newArrayList(staffId);

        return staffIdList;
    }

    public List<String> getTenantId(String appId, String token) {
        Claims claims = starToken.parseToken(token);
        String tenantId = String.valueOf(claims.get("tenant_id"));
        List<String> tenantIdList = Lists.newArrayList(tenantId);
        return tenantIdList;
    }


}
