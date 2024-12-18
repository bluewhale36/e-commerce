package com.guncat.ecommerce.security.config;

import com.guncat.ecommerce.security.annotation.AuthenticationPrincipalUserCodeArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * {@link com.guncat.ecommerce.security.annotation.AuthenticationPrincipalUserCode} Annotation 의 Resolver 인
 * {@link AuthenticationPrincipalUserCodeArgumentResolver} 에 대한 configuration.
 */
@Configuration
public class AuthenticationPrincipalUserCodeConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalUserCodeArgumentResolver());
    }
}
