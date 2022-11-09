package org.yeyu.springboot.gateway.provider.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.yeyu.springboot.gateway.provider.model.ResponseData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ResponseCoverFilter implements WebFilter, Ordered {

    /**
     * 返回值处理
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        exchange.getRequest().getQueryParams();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        // 释放掉内存
                        DataBufferUtils.release(join);
                        String responseData = new String(content, Charsets.UTF_8);
                        //此处拿到参数转为JSONObject 处理参数内容
                        JSONObject jsonObject = JSON.parseObject(responseData);
                        ResponseData<JSONObject> jsonObjectResult = ResponseData.buildSuccess(jsonObject);

                        responseData = JSONObject.toJSONString(jsonObjectResult, JSONWriter.Feature.MapSortField);
                        byte[] uppedContent = responseData.getBytes(Charsets.UTF_8);
                        originalResponse.getHeaders().setContentLength(uppedContent.length);
                        originalResponse.getHeaders().setAccessControlAllowOrigin("*");
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }

        };
        HttpHeaders httpHeaders = exchange.getResponse().getHeaders();
        httpHeaders.setAccessControlAllowOrigin("*");
        httpHeaders.set("access-control-allow-methods","*");
        httpHeaders.set("access-control-allow-headers","*");
        httpHeaders.setAccessControlAllowCredentials(true);
        decoratedResponse.getHeaders().setAccessControlAllowOrigin("*");
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return 101;
    }


}
