/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.events;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Dell
 */
public class BanUserEvent extends ApplicationEvent {
    
    private final String userName;
    private final boolean online;
 
    public BanUserEvent(String userName, boolean online) {
        super(userName);
        this.userName = userName;
        this.online = online;
    }
     
    public boolean getOnline() {
        return online;
    }

    
    
    
    public String getUserName() {
        return userName;
    }
    
}
