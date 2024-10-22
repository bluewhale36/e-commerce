package com.guncat.ecommerce.verification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 이메일 발송 시 사용되는 객체.
 */
@Getter
@Setter
@ToString
public class EmailDTO {

    private String receiver;            // 수신자
    private String subject;             // 제목
    private String body;                // 본문
    private MultipartFile attachment;   // 첨부 파일

}
