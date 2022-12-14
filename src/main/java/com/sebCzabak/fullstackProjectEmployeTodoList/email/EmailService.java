package com.sebCzabak.fullstackProjectEmployeTodoList.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

   // public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    // private final JavaMailSender javaMailSender;
    @Override
    public void send(String to, String email) {

    }
}
