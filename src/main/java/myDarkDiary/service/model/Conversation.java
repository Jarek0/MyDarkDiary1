/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "conversation")
public class Conversation {
    private Long id;
    private List<User> users;
    private List<Message> messages;
    
    public Conversation(){
        users=new ArrayList<User>();
        messages=new ArrayList<Message>();
    }
    public Conversation(List<User> users,List<Message> messages){
        this.users=users;
        this.messages=messages;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "conversation_user", joinColumns = @JoinColumn(name = "conversation_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
