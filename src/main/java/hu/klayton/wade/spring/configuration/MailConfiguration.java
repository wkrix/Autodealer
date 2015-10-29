package hu.klayton.wade.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Walter Krix <wkrix89@gmail.com>
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private int port;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        return mailSender;
    }

}
