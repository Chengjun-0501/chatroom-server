package com.example.bean;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Message {
    private Date date;   //当前时间
    private int myid;   //信息发出地址（id）
    private int toid;   //信息接收地址（id）
    private int msgid;   //消息id
    private String type;   //单聊为friend，群聊为room
    private String msg;   //信息文本内容

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMyid() {
        return myid;
    }

    public void setMyid(int myid) {
        this.myid = myid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
