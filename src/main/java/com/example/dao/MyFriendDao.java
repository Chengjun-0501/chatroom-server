package com.example.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MyFriendDao {
//    添加好友
    @Insert("Insert into friend (myid,id) VALUES (#{id},#{newfriendid}) ")
    void addfriend(int id,int newfriendid);

//    删除好友
    @Delete("DELETE FROM friend WHERE " +
            "(myid = #{friendid} and id = #{id}) or " +
            "(myid = #{id} and id = #{friendid})")
    void deletefriend(int id,int friendid);

}
