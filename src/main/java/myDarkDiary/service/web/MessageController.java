/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.web;


import java.util.Arrays;
import java.util.List;

import myDarkDiary.service.model.Conversation;
import myDarkDiary.service.model.Message;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.ConversationService;
import myDarkDiary.service.service.MessageService;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dell
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
      private UserService userService;
    @Autowired
      private ConversationService conversationService;
    
    @Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    private MessageService messageService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String displayMessages(Model model,Authentication auth) {
            User authUser=userService.findByUsername(auth.getName());
            List<Conversation> conversations=authUser.getConversations();
            model.addAttribute("conversations",conversations);
            return "message";
       
    }
    @RequestMapping(value = "/getMessages/{conversationId}",method = RequestMethod.GET)
    public @ResponseBody Message getMessages(@PathVariable(value="conversationId") Long conversationId){
        System.out.println("dddd");
        return conversationService.findById(conversationId).getMessages().get(0);
    }
    
    
    /*
    @RequestMapping(value = "/send/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sendMessage(@PathVariable(value="userId") Long id,@RequestBody String content,Model model,Authentication auth) {
        Message sendedMessage=new Message(userService.findByUsername(auth.getName()),userService.findById(id),content);
        Set<User> users=new HashSet<User>();
        users.add(userService.findByUsername(auth.getName()));
        users.add(userService.findById(id));
        Set<Conversation> conversation=conversationService.findByUsers(users);
        if(conversation.isEmpty()){
            conversation.add(new Conversation(users,sendedMessage));
            
        }
        else{
            conversation.iterator().next().getMessages().add(sendedMessage);
        }
        return new ResponseEntity<>(sendedMessage.getContent(), new HttpHeaders(), HttpStatus.ACCEPTED);
    }*/
    
    @RequestMapping(value = "/startConversation/{userId}",method = RequestMethod.GET)
    public String startConversation(@PathVariable(value="userId") Long id,Model model,Authentication auth) {
         
        User authUser=userService.findByUsername(auth.getName());
        try{
        User activeUser=userService.findById(id);
        
        
        List<Conversation> conversations=authUser.getConversations();
        
        
        if(!conversationService.haveUserConversation(conversations, activeUser)){
            Conversation conversation=new Conversation();
            List<User> users=conversation.getUsers();
            users.add(authUser);
            users.add(activeUser);
            conversations.add(conversation);
            Message message=new Message(authUser,activeUser,"User: "+authUser.getUsername()+" start conversation with you",conversation);
            if(conversation.getMessages()==null)
            {
            conversation.setMessages(Arrays.asList(message));
            }
            else{
            conversation.getMessages().add(message);
            }
            conversationService.save(conversation);
        }
        
            model.addAttribute("conversations",conversations);
            model.addAttribute("activeUser", activeUser);
       return "message";
       }
       catch(Exception e){
             List<Conversation> conversations=authUser.getConversations();
             model.addAttribute("conversations",conversations);
             
             return "message";
        }
    }
    
    @MessageMapping("/send")
    @SendToUser("/user")
    public void send(Message message) throws Exception {
        template.convertAndSendToUser(message.getRecipient().getUsername(),"/reply", message);
    }
    /*
    @MessageExceptionHandler
    @SendToUser(destinations="/queue/errors", broadcast=false)
    public ApplicationError handleException(MyBusinessException exception) {
        // ...
        return appError;
    }*/
    
}
