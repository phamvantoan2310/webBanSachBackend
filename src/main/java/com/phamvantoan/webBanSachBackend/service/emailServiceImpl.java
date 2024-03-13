package com.phamvantoan.webBanSachBackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class emailServiceImpl implements emailService{
    private JavaMailSender javaMailSender;
    @Autowired
    public emailServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendMessage(String from, String to, String subject, String content) {  //phương thức gửi email
        // MimeMailMessage => có đính kèm media
        // SimpleMailMessage => nội dung thông thường

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMessage);
    }
}
