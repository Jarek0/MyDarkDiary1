/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.repository;


import java.util.List;
import myDarkDiary.service.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dell
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{
    
    Conversation findById(Long id);
    
    
}
