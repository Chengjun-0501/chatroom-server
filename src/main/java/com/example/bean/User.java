package com.example.bean;

import org.springframework.stereotype.Component;

@Component

public class User {
    private int id;   //用户id，也是登录账号
    private String name;   //用户昵称
    private String img;   //用户头像
//    private String account;   //
    private String psword;   //用户密码
    private MyFriend[] MyFriendList;   //好友id列表
    private Room[] RoomList;   //聊天id列表

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPsword() {
        return psword;
    }

    public void setPsword(String psword) {
        this.psword = psword;
    }

    public MyFriend[] getMyFriendList() {
        return MyFriendList;
    }

    public void setMyFriendList(MyFriend[] myFriendList) {
        MyFriendList = myFriendList;
    }

    public Room[] getRoomList() {
        return RoomList;
    }

    public void setRoomList(Room[] roomList) {
        RoomList = roomList;
    }
}
