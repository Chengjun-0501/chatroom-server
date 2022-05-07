package com.example.dao;

import com.example.bean.MyFriend;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface RoomDao {
//    添加聊天室成员
    @Insert("Insert into room (id,roomId) VALUES (#{newmembersid},#{roomid}) ")
    void addroommembers(int roomid, int newmembersid);

//    创建聊天室
    @Insert("Insert into room (id,roomId,roomname,date) VALUES (#{id},#{newroomid},#{newroomname},#{newroomdate}) ")
    void createroom(int id, int newroomid, String newroomname, String newroomdate);

//    修改聊天室名称
    @Update("update room set roomname = #{newroomname} where roomId = #{roomid} and id = #{id}")
    void updateroomname(int id, String newroomname, int roomid);

//    删除并推出聊天室
    @Delete("DELETE FROM room WHERE id = #{id} and roomId = #{roomid} ")
    void deleteroom(int id, int roomid);

//    查看聊天室成员
    @Select("Select user.id,name,img from user,room where roomId = #{roomid} and room.id = user.id")
    ArrayList<MyFriend> selectroommembers(int roomid);

}
