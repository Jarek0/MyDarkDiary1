/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import java.util.List;
import myDarkDiary.service.model.Conversation;
import myDarkDiary.service.model.User;

/**
 *
 * @author Dell
 */
public interface ConversationService {
    
    Conversation findById(Long id);
    boolean haveUserConversation(List<Conversation> conversations,User user);
    void save(Conversation conversation);
}
