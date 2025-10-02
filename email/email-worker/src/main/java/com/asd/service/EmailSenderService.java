package com.asd.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${username}@naver.com");
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + code);
        mailSender.send(message);

        System.out.println("이메일 발송 완료: " + email);
    }
}