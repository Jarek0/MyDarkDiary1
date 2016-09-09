/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.listeners;

import myDarkDiary.service.events.OnRegistrationCompleteEvent;
import java.util.UUID;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.MailServiceImpl;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailSender;

import org.springframework.stereotype.Component;


/**
 *
 * @author Dell
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private MailServiceImpl mailService;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
         
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());
         
        
        mailService.sendMail("from@no-spam.com",recipientAddress,subject,message + " rn " + "http://localhost:8080" + confirmationUrl);
    }
}
