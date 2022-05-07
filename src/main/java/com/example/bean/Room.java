package com.example.bean;

import java.util.Date;

public class Room {   //聊天房间
    private String roomname;   //房间名
    private String msg;   //信息
    private int roomId;   //房间id
    private Date lastDate;   //上次信息的时间
    //    private String img;   //图片（暂未加入
//    private int tips;   //暂未加入
    private MyFriend[] members;   //成员id列表


    /**
    @Override
    public String toString() {    //
        return "Room{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", roomId=" + roomId +
                ", lastDate=" + lastDate +
//                ", img='" + img + '\'' +
//                ", tips=" + tips +
                '}';
    }
**/

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public MyFriend[] getMembers() {
        return members;
    }

    public void setMembers(MyFriend[] members) {
        this.members = members;
    }
//
//    public void setTips(int tips) {
//        this.tips = tips;
//    }

//    public int getTips() {
//        return tips;
//    }

//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
}
