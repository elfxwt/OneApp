package com.example.sophia_xu.robotChat;

import java.util.Date;

/**
 * Created by Sophia_Xu on 2015/7/16.
 */
public class ChatMessage {


    private Type type;
    private Date date;
    private String Msg;
    private String name;

    public ChatMessage() {

    }

    public enum Type{
        INCOMING,OUTCOMING
    }

    public ChatMessage(Type type,Date date,String msg){
        this.type = type;
        this.date = date;
        this.Msg = msg;

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }





}
