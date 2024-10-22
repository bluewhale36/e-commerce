package com.guncat.ecommerce.verification.service;

import com.guncat.ecommerce.verification.dto.EmailDTO;

public interface IF_VerificationService {

    public String sendMail(EmailDTO emailDTO);
}
