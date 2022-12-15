//package com.sebCzabak.fullstackProjectEmployeTodoList.email;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService  {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//     private final JavaMailSender javaMailSender;    @Bean
//    @Override
//    @Async
//    public void send(String to, String email) {
//    try{
//        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
//        helper.setText(email,true);
//        helper.setTo(to);
//        helper.setSubject("Confirm your account.");
//        helper.setFrom("fullstackproject");
//    }catch (MessagingException exception){
//        LOGGER.error("failed to send email.",exception);
//        throw new IllegalStateException("failed to send email");
//
//    }
//
//
