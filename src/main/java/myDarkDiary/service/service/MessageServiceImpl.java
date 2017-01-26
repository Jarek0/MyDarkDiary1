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
import myDarkDiary.service.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dell
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRespository;
    
    @Override
    public Message findById(Long id) {
        return messageRespository.findById(id);
    }

    @Override
    public Set<Message> findBySender(User sender) {
        return messageRespository.findBySender(sender);
    }

    @Override
    public Set<Message> findByRecipient(User recipient) {
        return messageRespository.findByRecipient(recipient);
    }

    @Override
    public Set<Message> findByDate(Date date) {
      return messageRespository.findByDate(date);
    }
    @Transactional
    @Override
    public void save(Message message) {
     messageRespository.save(message);
    }
    
}
