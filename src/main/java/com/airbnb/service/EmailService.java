package com.airbnb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

//package com.example.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

//@Service
//public class EmailService {

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendEmailWithAttachment(String to, String subject, String text, File file) throws MessagingException, IOException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indicates multipart message
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(text);
//
//        if (file != null) {
//            helper.addAttachment(file.getName(), file);
//        }
//
//        javaMailSender.send(mimeMessage);
//    }
//}


