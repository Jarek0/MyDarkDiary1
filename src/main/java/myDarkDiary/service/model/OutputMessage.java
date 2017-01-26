/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.model;

/**
 *
 * @author Dell
 */
public class OutputMessage {
    String time;
    String text;
    String from;
    
    public OutputMessage(String from,String text,String time){
        this.time=time;
        this.text=text;
        this.from=from;
    }
    
    public void setTime(String time){
        this.time=time;
    }
    public String getTime(){
        return time;
    }
    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return text;
    }
    public void setFrom(String from){
        this.from=from;
    }
    public String getFrom(){
        return from;
    }
}
