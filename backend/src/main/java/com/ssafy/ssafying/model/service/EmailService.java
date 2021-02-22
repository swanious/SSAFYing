package com.ssafy.ssafying.model.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public String sendSimpleMessage(String to) throws Exception{
        String uuid = UUID.randomUUID().toString().replace("-", "");
        uuid = uuid.substring(0, 5);
        SimpleMailMessage message = createMessage(to, "[SSAFYFING] 회원가입 이메일 인증", uuid);
        emailSender.send(message);
        return uuid;
    }
     
    private SimpleMailMessage createMessage(String to, String subject, String text) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
