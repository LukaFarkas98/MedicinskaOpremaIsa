package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.entity.User;
import LukaFarkas.MedOpremaBackend.entity.VerificationToken;
import LukaFarkas.MedOpremaBackend.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    /*
     * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
     */
    @Autowired
    private Environment env;

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */

    @Async
    public void sendEmailResponse(String userEmail, String response) {
        // Implement the method to send an email using JavaMailSender
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Your Complaint Response");
        message.setText(response);

        javaMailSender.send(message);
    }

    @Async
    public void sendVerificationEmail(User user) throws MailException, InterruptedException {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(verificationToken);

        String verificationUrl = "http://localhost:8080/api/verify?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Complete your registration");
        mail.setText("Dear " + user.getFirstName() + ",\n\nPlease click the link below to complete your registration:\n" + verificationUrl + "\n\nThe link will expire in 24 hours.");
        javaMailSender.send(mail);

        System.out.println("Verification email sent!");
    }


}
