/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.events;

import myDarkDiary.service.events.*;
import java.util.Locale;
import myDarkDiary.service.model.User;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Dell
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final String appUrl;
    private final Locale locale;
    private final User user;
 
    public OnRegistrationCompleteEvent(
      User user, Locale locale, String appUrl) {
        super(user);
         
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
     
    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }
    
    public User getUser() {
        return user;
    }
    
}