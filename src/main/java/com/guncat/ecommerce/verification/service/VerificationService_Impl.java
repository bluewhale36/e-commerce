package com.guncat.ecommerce.verification.service;

import com.guncat.ecommerce.verification.dto.EmailDTO;
import com.guncat.ecommerce.verification.exception.MailNotSentException;
import com.guncat.ecommerce.verification.util.VerificationCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationService_Impl implements IF_VerificationService {

    private final JavaMailSender mailSender;

    @Override
    public String sendMail(EmailDTO emailDTO) {
        String verificationCode = VerificationCode.generateCode();
        emailDTO.setSubject("GUNCAT MALL 인증 코드");
        emailDTO.setBody(
                "<h3>Verification Code</h3>" +
                "<h1 style=\"font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif\">"+verificationCode+"</h1>"
        );

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            messageHelper.setTo(emailDTO.getReceiver());
            messageHelper.setSubject(emailDTO.getSubject());
            messageHelper.setText(emailDTO.getBody(), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailNotSentException("메일 전송 실패.");
        }

        return verificationCode;
    }

}
