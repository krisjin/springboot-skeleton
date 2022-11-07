package org.yeyu.springboot.gateway.provider.filter;

import com.lucas.gateway.common.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author kris
 * @date 2022/9/27
 */

//@Component
//@NoArgsConstructor
//@Setter
//@Getter
public class CorsWebFilterConfiguration {
//    @Bean
//    CorsWebFilter corsFilter() {
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        //允许全部域名
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.setAllowedMethods(Arrays.asList(Constants.ACCESS_CONTROL_ALLOW_METHODS_VALUE.split(",")));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsWebFilter(source);
//    }
}