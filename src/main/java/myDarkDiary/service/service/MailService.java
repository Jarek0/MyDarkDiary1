/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Dell
 */
public interface MailService {
    public void sendMail(String from, String to, String subject, String body);
    public void sendMail(SimpleMailMessage message);
}
