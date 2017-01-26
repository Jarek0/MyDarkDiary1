/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import myDarkDiary.service.repository.ConversationRepository;
import java.util.Iterator;
import java.util.List;
import myDarkDiary.service.model.Conversation;
import myDarkDiary.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dell
 */
@Service
public class ConversationServiceImpl implements ConversationService{
    @Autowired
    private ConversationRepository conversationRespository;
    
    
    @Override
    public boolean haveUserConversation(List<Conversation> conversations,User user){
        if(conversations == null)
        {
            System.out.println("aaaa");
            return false;
        }
        
            Iterator<Conversation> it = conversations.iterator();
            while (it.hasNext()) {
                Iterator<User> jt = it.next().getUsers().iterator();
                while (jt.hasNext()){
                    if (jt.next().getId().equals(user.getId())) {
                        return true;
                    } 
            
            }
            }
        
        return false;
    }
    @Override
    public Conversation findById(Long id){
        return conversationRespository.findById(id);
    }
    @Transactional
    @Override
    public void save(Conversation conversation){
        conversationRespository.save(conversation);
    }
    
}
