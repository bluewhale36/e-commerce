package com.guncat.ecommerce.verification.dto;

/**
 * Session 에 이메일 인증 정보 저장 시 사용되는 DTO 객체.
 *
 * @see java.lang.Record
 * @param email Email 주소 문자열.
 * @param verificationCode 인증 코드 문자열.
 */
public record SessionVerificationDTO(String email, String verificationCode) {}
