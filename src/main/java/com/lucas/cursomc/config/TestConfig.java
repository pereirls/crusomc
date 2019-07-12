package com.lucas.cursomc.config;

import com.lucas.cursomc.services.DbService;
import com.lucas.cursomc.services.EmailService;
import com.lucas.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DbService dbService;
    @Autowired
    private JavaMailSender javaMailSender;

   @Bean
    public boolean instantiateDataBase() throws Exception {
        dbService.instantiateDastaBase();
        return true;
    }

    @Bean
    public EmailService emailService() {
       return new MockEmailService();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
