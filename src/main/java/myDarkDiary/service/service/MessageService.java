/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import java.util.Date;
import java.util.Set;
import myDarkDiary.service.model.Message;
import myDarkDiary.service.model.User;

/**
 *
 * @author Dell
 */
public interface MessageService {
    Message findById(Long id);
    Set<Message> findBySender(User sender);
    Set<Message> findByRecipient(User recipient);
    Set<Message> findByDate(Date date);
    public void save(Message message);
}
