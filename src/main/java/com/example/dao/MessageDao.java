package com.example.dao;

import com.example.bean.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
@Mapper
public interface MessageDao {
//    发送消息
    @Insert("Insert into message (date,id,toid,msg) VALUES (#{date},#{id},#{toid},#{newmessage}) ")
    void insertmessage( Date date, int id, int toid,String newmessage);

//    查看历史消息
    @Select("Select date,myid,toid,roomid,type,msg from message WHERE myid = #{userid} or toid = #{userid}")// or toid = #{id}
    ArrayList<Message> selectmessage(int userid);

//    删除历史消息
//    @Delete("DELETE * FROM message WHERE id = #{id} and toid = #{toid}")
//    void deletemessage(int id,int toid);
}
