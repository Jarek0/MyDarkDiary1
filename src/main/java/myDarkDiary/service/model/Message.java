/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "message")
public class Message {
    private static final int EXPIRATION = 60 * 24;
    private Long id;
    private User sender;
    private User recipient;
    private Long date;
    private String content;
    private Conversation conversation;
    private boolean received;
    
    public Message(){
        
    }
    public Message(User sender,User recipient,String content,Conversation conversation){
        this.sender = sender;
        this.recipient = recipient;
        this.date = calculateExpiryDate(EXPIRATION).getTime();
        this.conversation=conversation;
        this.content = content;
    }
    
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
    
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    public User getRecipient() {
        return recipient;
    }
    

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    public Conversation getConversation() {
        return conversation;
    }
    

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public Long getDate() {
        return date;
    }
    
    
    public String displayDate(){
        return DateFormat.getInstance().format(date);
    }

    public void setDate(Long date) {
        this.date = date;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    } 
    public boolean getReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
