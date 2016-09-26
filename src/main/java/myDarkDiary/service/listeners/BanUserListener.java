/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.listeners;

/**
 *
 * @author Dell
 */

import java.util.List;
import myDarkDiary.service.events.BanUserEvent;
import myDarkDiary.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import org.springframework.stereotype.Component;


/**
 *
 * @author Dell
 */
@Component
public class BanUserListener implements ApplicationListener<BanUserEvent> {
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    
 
    @Override
    public void onApplicationEvent(BanUserEvent event) {
        try {
            this.confirmBan(event);
        } catch (Exception ex) {
        }
    }
 
    private void confirmBan(BanUserEvent event)throws Exception {
        String bannedUser = event.getUserName();
        if(event.getOnline())
            {
                org.springframework.security.core.userdetails.User bannedUserWithSession=findUserByUsername(bannedUser);
                if(bannedUserWithSession!=null)
                {
                    for(SessionInformation session:(sessionRegistry.getAllSessions(bannedUserWithSession, false))){
                        session.expireNow();
                    }
                }
            }
        
    }

    public org.springframework.security.core.userdetails.User findUserByUsername(String userName)
        {
            List<Object> onlineUserList=sessionRegistry.getAllPrincipals();
            for(Object onlineUser:onlineUserList)
            {
                org.springframework.security.core.userdetails.User convertedonlineUser=(org.springframework.security.core.userdetails.User)onlineUser;
                if(((convertedonlineUser).getUsername()).equalsIgnoreCase(userName))
                {
                   return convertedonlineUser; 
                }
                
            }
            return null;
        }
    
}
