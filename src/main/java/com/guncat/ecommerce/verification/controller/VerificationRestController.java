package com.guncat.ecommerce.verification.controller;

import com.guncat.ecommerce.verification.dto.EmailDTO;
import com.guncat.ecommerce.verification.dto.SessionVerificationDTO;
import com.guncat.ecommerce.verification.exception.CodeNotMatchException;
import com.guncat.ecommerce.verification.exception.VerificationInfoNotFoundException;
import com.guncat.ecommerce.verification.service.IF_VerificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Email 인증 관련 request 를 처리하는 Rest Controller Class.
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class VerificationRestController {

    private final IF_VerificationService verificationService;

    /**
     * Email 인증 코드 발송 요청 처리.
     * @param receiver 수신자 Email 주소 문자열.
     * @param session 세션 작업 처리 목적의 {@link HttpSession} 객체.
     */
    @PostMapping("")
    public void sendVerificationEmail(@RequestParam("email") final String receiver,
                                      final HttpSession session) {
        // 메일 전송을 위한 DTO 객체 생성.
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setReceiver(receiver);

        // 인증 코드 메일 전송 및 반환.
        String code = verificationService.sendMail(emailDTO);

        // 반환된 인증 코드가 존재 할 경우.
        // 존재하지 않을 경우 MailNotSentException 으로 예외 처리 됨. (Service Layer)
        if (!code.isEmpty()) {
            SessionVerificationDTO sessionVerificationDTO = new SessionVerificationDTO(receiver, code);
            // 세션에 인증 코드 정보 저장.
            // 추후 form submit 시 인증 코드 동일 여부 판단.
            // value : 전송된 인증 코드.
            session.setAttribute("verification", sessionVerificationDTO);
            // 5분 후 만료. (5 * 60s)
            session.setMaxInactiveInterval(5 * 60);
        }
    }

    /**
     * Email 인증 코드 일치 여부 판단 요청 처리.
     * @param code Email 에 대한 인증 코드 문자열.
     * @param session 세션 작업 처리 목적의 {@link HttpSession} 객체.
     * @return {@code true} Email 과 그 인증 코드가 일치할 경우.
     * @throws VerificationInfoNotFoundException 기존 session 객체가 반환되지 않는 경우. <br/>
     * @throws CodeNotMatchException 세션의 인증 코드가 Email 과 일치하지 않는 경우.
     */
    @PostMapping("/code")
    public boolean verifyEmailAndCode(@RequestParam("code") final String code,
                                      final HttpSession session) {
        // 생성되었던 인증 코드 반환.
        SessionVerificationDTO sessionVerificationDTO = (SessionVerificationDTO) session.getAttribute("verification");

        // 해당 값이 존재 할 경우.
        if (sessionVerificationDTO != null) {

            // 그 값이 code 와 일치 할 경우.
            if (sessionVerificationDTO.verificationCode().equals(code)) {
                // 유효시간 초기화. -> 회원 가입 시 재확인.
                session.setMaxInactiveInterval(5 * 60);
                return true;
            } else {
                throw new CodeNotMatchException("일치하지 않습니다.");
            }
        } else {
            throw new VerificationInfoNotFoundException("인증 정보를 찾을 수 없습니다.");
        }
    }

}
