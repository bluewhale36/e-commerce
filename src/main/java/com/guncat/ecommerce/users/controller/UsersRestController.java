package com.guncat.ecommerce.users.controller;

import com.guncat.ecommerce.users.dto.LoginDTO;
import com.guncat.ecommerce.users.dto.RegisterDTO;
import com.guncat.ecommerce.users.exception.DuplicatedInfoException;
import com.guncat.ecommerce.users.service.IF_UsersService;
import com.guncat.ecommerce.verification.dto.SessionVerificationDTO;
import com.guncat.ecommerce.verification.exception.CodeNotMatchException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 request 를 처리하는 Rest Controller Class.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersRestController {

    private final IF_UsersService usersService;
    private final UserDetailsService userDetailsService;

    /**
     * 사용자 로그인 요청.
     * @param loginDTO 로그인 정보 객체.
     */
    @PostMapping("/login")
    public void login(@ModelAttribute LoginDTO loginDTO) {
        userDetailsService.loadUserByUsername(loginDTO.getUserId());
    }

    /**
     * 사용자의 최종 회원 가입 요청.<br/>
     * 아이디 중복 체크, 이메일 중복 체크 및 인증이 완료된 후 실행되는 request.<br/>
     * 위의 중복 확인 및 인증 여부 재확인 후 Service Layer 호출.
     * @param registerDTO 가입 시 요구되는 데이터 DTO 객체.
     * @param session 이메일 인증 코드 저장.
     * @throws CodeNotMatchException 인증 코드가 일치하지 않을 경우.
     * @throws DuplicatedInfoException 아이디 또는 이메일이 중복되었을 경우.
     */
    @PostMapping("/join")
    public void join(@ModelAttribute RegisterDTO registerDTO,
                       HttpSession session) {

        // userId, Email 중복 재확인
        if (
                usersService.chkDuplicatedUserId(registerDTO.getUserId()) == 0L
                && usersService.chkDuplicatedEmail(registerDTO.getEmail()) == 0L
        ) {
            SessionVerificationDTO sessionVerificationDTO =
                    (SessionVerificationDTO) session.getAttribute("verification");
            // email 인증 코드 재확인
            if (
                    sessionVerificationDTO.email().equals(registerDTO.getEmail())
                    && sessionVerificationDTO.verificationCode().equals(registerDTO.getVerificationCode())
            ) {
                usersService.register(registerDTO);
            } else {
                throw new CodeNotMatchException("인증코드가 없거나 유효하지 않습니다.");
            }
        } else {
            throw new DuplicatedInfoException("중복된 아이디 또는 이메일입니다.");
        }
    }

    /**
     * 중복된 사용자 ID 존재 여부 판단 요청 처리.
     * @param userId 중복 여부를 판단할 사용자 ID 문자열.
     * @return 중복된 사용자 ID 가 있을 경우 {@code 1L} 이상, 없을 경우 {@code 0L}.
     */
    @PostMapping("/dup/userid")
    public Long chkDuplicatedUserId(@RequestParam String userId) {
        return usersService.chkDuplicatedUserId(userId);
    }

    /**
     * 중복된 Email 존재 여부 판단 요청 처리.
     * @param email 중복 여부를 판단할 Email 문자열.
     * @return 중복된 Email 이 있을 경우 {@code 1L} 이상, 없을 경우 {@code 0L}.
     */
    @PostMapping("/dup/email")
    public Long chkDuplicatedEmail(@RequestParam String email) {
        return usersService.chkDuplicatedEmail(email);
    }

}
