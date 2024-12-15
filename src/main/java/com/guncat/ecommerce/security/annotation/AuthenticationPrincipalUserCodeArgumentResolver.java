package com.guncat.ecommerce.security.annotation;

import com.guncat.ecommerce.security.domain.UserDetails_Impl;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link AuthenticationPrincipalUserCode} annotation 이 사용된 controller method 의 parameter 에 대한
 * 커스텀 {@link HandlerMethodArgumentResolver} 클래스. 현재 인증된 사용자의 {@code userCode} 를
 * {@link SecurityContextHolder} 로부터 추출하여 반환한다.<br/><br/>
 *
 * Controller method 의 매개변수 중 {@link AuthenticationPrincipalUserCode} annotation 이 사용되어야 하며 해당 매개변수의 타입이
 * {@link String} 이어야 한다. 이 외의 경우 해당 resolver 는 동작하지 않는다.<br/><br/>
 *
 * 매개변수를 resolve 할 때 {@link SecurityContextHolder} 에서 {@link Authentication} 객체가 추출된다. 해당 객체의 {@code principal} 값은
 * {@link UserDetails_Impl} 이어야 한다. 해당 인스턴스에서, {@code userCode} 값은 {@link com.guncat.ecommerce.users.dto.UsersDTO} 객체로부터 반환된다.
 * 위 조건에 해당되지 않는 경우 {@link IllegalArgumentException} 이 발생한다.
 */
public class AuthenticationPrincipalUserCodeArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     * 현재 resolver 가 해당 annotation 에 사용될 수 있는 지의 여부를 판단하는 method.<br/>
     * {@code true} 를 반환할 경우 {@link AuthenticationPrincipalUserCodeArgumentResolver#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)} 가 실행된다.<br/>
     * 그렇지 않을 경우 해당 resolver 는 동작하지 않는다.
     *
     * @param parameter the method parameter to check
     * @return parameter 인 {@link MethodParameter} 가 {@link AuthenticationPrincipalUserCode} annotation 과 사용되었으며,
     * 사용된 매개변수의 타입이 {@link String} 일 경우 {@code true}. 그렇지 않을 경우 {@code false}.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // Resolve 하는 Annotation 의 타입 명시
        boolean hasCorrectAnnotation = parameter.hasParameterAnnotation(AuthenticationPrincipalUserCode.class);

        // 사용된 Annotation 의 parameter type 이 String 인 여부
        boolean isString = parameter.getParameterType().equals(String.class);

        return hasCorrectAnnotation && isString;
    }

    /**
     * 현재 인증된 사용자의 {@code userCode} 값을 바인딩하는 method.
     *
     * @return 현재 인증된 사용자의 {@code userCode} 데이터.
     * @throws IllegalArgumentException annotation 이 사용된 매개변수가 {@link String} 타입이 아니거나,
     * {@link SecurityContextHolder} 로부터 추출한 {@link Authentication} 객체가 {@code null} 일 경우,
     * 또는 {@link Authentication} 객체의 {@code principal} 변수가 {@link UserDetails_Impl} 의 인스턴스가 아닌 경우.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*
            SecurityContextHolder 에서 Authentication 을 추출하고, 해당 객체에서 principal 값을 추출한다.
            이 때 principal 은 UserDetails_Impl 객체여야 한다.
            principal 이 정상적으로 리턴되었을 경우 UserDetails_Impl 의 userCode 값을 return 한다.
         */

        // 해당 annotation 을 사용하는 매개변수 타입이 String 이 아닐 경우 exception throw.
        if (!parameter.getParameterType().equals(String.class)) {
            throw new IllegalArgumentException("Only String Type Supported");
        }

        // SecurityContextHolder 로부터 Authentication 객체 반환.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentication 객체가 null 값이 아니고, 그 principal 변수의 자료형이 UserDetails_Impl 일 경우
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails_Impl userDetails) {
            // principal 인 UserDetails_Impl 의 usersDTO 로부터 userCode 를 반환한다.
            return userDetails.getUsersDTO().getUserCode();
        } else {
            // 이 외의 경우 반환할 수 없으므로 exception 을 발생시킨다.
            throw new IllegalArgumentException("Not Available");
        }


    }
}
