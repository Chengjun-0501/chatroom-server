package com.example.dao;

import com.example.bean.MyFriend;
import com.example.bean.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface UserDao {
    //    查看用户信息
    @Select("Select * from user where id = #{myid}")
    MyFriend selectuserinfo(int myid);

    //    查看好友
    @Select("Select id,name,img from user where id in " +
            "(select id from friend where myid = #{myid} UNION " +
            "select myid from friend where id = #{myid}) ")
    ArrayList<MyFriend> selectuserfriend(int myid);

    //    查看聊天室
    @Select("Select roomId,roomname from room where id = #{myid} ")
    ArrayList<Room> selectuserroom(int myid);

    //    查询密码
    @Select("Select psword from user where id = #{myid}")
    String selectuserpsword(int myid);

    //    修改用户头像
    @Update("update user set img = #{newuserimg} where id = #{myid}")
    void updateuserimg(String newuserimg, int myid);

//    修改用户名
    @Update("update user set name = #{newusername} where id = #{myid}")
    void updateusername(String newusername, int myid);

//    修改用户密码
    @Update("update user set psword = #{newuserpsword} where id = #{myid}")
    void updateuserpsword(String newuserpsword, int myid);

////    注册用户
//    @Insert("Insert into user (id,name,img,psword) VALUES (#{id},#{name},#{img},#{psword})")
//    void insertuser(int id,String name,String img,String psword);

//    注册用户
    @Insert("Insert into user (id,name,psword) VALUES (#{myid},#{name},#{psword})")
    void insertuser(int myid, String name, String psword);

}
