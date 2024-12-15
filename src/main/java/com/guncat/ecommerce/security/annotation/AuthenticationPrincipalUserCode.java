package com.guncat.ecommerce.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 method 의 String type parameter 에서 현재 인증된 사용자의
 * {@code userCode} 데이터를 반환하도록 하기 위한 커스텀 Annotation.<br/><br/>
 * 해당 Annotation 을 사용하는 매개변수의 타입은 {@link String} 이어야 하며, 이 외의 경우
 * {@link IllegalArgumentException} 이 발생한다.<br/><br/>
 * Controller method 의 매개변수에서만 사용 가능하다.
 *
 * @see AuthenticationPrincipalUserCodeArgumentResolver
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticationPrincipalUserCode {
}
