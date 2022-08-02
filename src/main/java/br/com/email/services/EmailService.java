package br.com.email.services;

import br.com.email.dtos.EmailDto;
import br.com.email.enums.StatusEmail;
import br.com.email.models.EmailModel;
import br.com.email.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final EmailRepository emailRepository;

    private final JavaMailSender emailSender;


    public EmailModel sendEmail(EmailModel emailModel) {
       emailModel.setSendDateEmail(LocalDateTime.now());
       try{
           SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom(emailModel.getEmailFrom());
           message.setTo(emailModel.getEmailTo());
           message.setSubject(emailModel.getSubject());
           message.setText(emailModel.getText());
           emailSender.send(message);

           emailModel.setStatusEmail(StatusEmail.SENT);
       }catch (MailException e){
           emailModel.setStatusEmail(StatusEmail.ERROR);
       }finally {
           return emailRepository.save(emailModel);
       }
    }
}
