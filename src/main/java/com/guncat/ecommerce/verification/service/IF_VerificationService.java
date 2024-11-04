package com.guncat.ecommerce.verification.service;

import com.guncat.ecommerce.verification.dto.EmailDTO;
import com.guncat.ecommerce.verification.util.VerificationCode;

/**
 * 메일 발송 관련 Service Layer method 정의.
 */
public interface IF_VerificationService {

    /**
     * 메일 발송.
     * @param emailDTO {@link EmailDTO} 객체.
     * @return {@link VerificationCode#generateCode()}
     */
    public String sendMail(EmailDTO emailDTO);
}
