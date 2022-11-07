package org.yeyu.springboot.gateway.provider.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lucas.gateway.common.Constants;
import com.lucas.gateway.enums.JwtSecret;
import com.lucas.gateway.jwt.JwtUtils;
import com.lucas.gateway.provider.filter.model.GatewayContext;
import com.lucas.gateway.provider.service.TokenAdapter;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class ModifyRequestResponseFilter implements GlobalFilter, Ordered {

    @Resource
    private TokenAdapter tokenAdapter;

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        // 响应体
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpRequestDecorator requestDecorator = processRequest(exchange, request, bufferFactory);
        ServerHttpResponseDecorator responseDecorator = processResponse(response, bufferFactory);
        return chain.filter(exchange.mutate().request(requestDecorator).response(responseDecorator).build());
    }

    private ServerHttpRequestDecorator processRequest(ServerWebExchange exchange, ServerHttpRequest request, DataBufferFactory bufferFactory) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        return new ServerHttpRequestDecorator(request) {

            @Override
            public ServerHttpRequest getDelegate() {
                return super.getDelegate();
            }

            @Override
            public MultiValueMap<String, String> getQueryParams() {
                GatewayContext gatewayContext = (GatewayContext) exchange.getAttributes().get(GatewayContext.CACHE_GATEWAY_CONTEXT);
                return gatewayContext.getFormData();
            }

            @Override
            public HttpMethod getMethod() {
                return HttpMethod.POST;
            }

            @Override
            public String getMethodValue() {
                return getMethod().name();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(request.getHeaders());
                httpHeaders.remove(HttpHeaders.AUTHORIZATION);
                httpHeaders.remove(HttpHeaders.HOST);
                log.info("====request host:" + JSON.toJSONString(headers.get(HttpHeaders.HOST)));

                httpHeaders.put(HttpHeaders.HOST, Lists.newArrayList("app-api-v2-test.lucasgchr.com"));
//                httpHeaders.put(HttpHeaders.HOST, Lists.newArrayList("astro-career-api-test.lucasgchr.com"));

                List<String> requestContent = httpHeaders.get(HttpHeaders.CONTENT_TYPE);
                log.info("requestContent=====" + requestContent);
                log.info("request header=====" + JSONObject.toJSONString(httpHeaders));


                //下发调用 http post 根据目标方法设置
                httpHeaders.put(HttpHeaders.CONTENT_TYPE, Lists.newArrayList(Constants.HTTP_CONTENT_JSON_TYPE));

                List<String> tokenList = headers.get(Constants.HTTP_HEADER_TOKEN);
                List<String> appIdList = headers.get(Constants.HTTP_HEADER_APPID);

                String token = null;
                String appId = null;
                if (tokenList != null && tokenList.size() > 0) {
                    token = tokenList.get(0);
                    log.info("header token is " + token);
                } else {
                    log.error("must token header is null...");
                }
                if (appIdList != null && appIdList.size() > 0) {
                    appId = appIdList.stream().findFirst().get();
                } else {
                    log.error("must appId header is null...");
                }

                //解析token，获取userId
                if (StringUtils.isNotEmpty(appId) && StringUtils.isNotEmpty(token)) {
                    List<String> userIdList = tokenAdapter.getUserId(appId, token);
                    httpHeaders.put(Constants.HTTP_HEADER_LUCAS_USER_ID, userIdList);
                } else {
                    //默认使用星职场token解析
                    Claims claims = JwtUtils.parseJwt(token, JwtSecret.STAR.getSecret());
                    String userId = String.valueOf(claims.get(Constants.JWT_USER_ID));
                    httpHeaders.put(Constants.HTTP_HEADER_LUCAS_USER_ID, Lists.newArrayList(userId));
                }

                List<String> staffId = tokenAdapter.getStaffId(appId, token);
                List<String> tenantId = tokenAdapter.getTenantId(appId, token);

                if (staffId != null && staffId.size() > 0) {
                    httpHeaders.put(Constants.HTTP_HEADER_LUCAS_STAFF_ID, staffId);
                }
                if (tenantId != null && tenantId.size() > 0) {
                    httpHeaders.put(Constants.HTTP_HEADER_LUCAS_TENANT_ID, tenantId);
                }


                //是否需要转换token
                httpHeaders.put(HttpHeaders.AUTHORIZATION, Lists.newArrayList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNzE2NDIxIiwiZXhwIjoxNjY0NjcyNTY0LCJkb21haW4iOiJodHRwczpcL1wvY29sdW1idXMtYXBwLWFwaS10ZXN0Lmx1Y2FzZ2Noci5jb21cLyJ9.NptNqWaIvVY0mi7a7q-CzW8b43m1qs0Htt88VMOtREY"));

                httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return request.getBody();
            }
        };
    }

    private ServerHttpResponseDecorator processResponse(ServerHttpResponse response, DataBufferFactory bufferFactory) {
        response.getHeaders().setAccessControlAllowOrigin("*");

        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                return super.writeWith(body);
            }
        };
    }


}