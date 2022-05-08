package com.example.service;

import com.example.bean.Result;
import com.example.dao.MessageDao;
import com.example.dao.MyFriendDao;
import com.example.dao.RoomDao;
import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    MyFriendDao myFriendDao;

    @Autowired
    MessageDao messageDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    private RedisTemplate redisTemplate;

//    登录
    public Result login(int userid, String userpsword){
        Result result = new Result();
        String psword = userDao.selectuserpsword(userid);
        if(userpsword.equals(psword)) {
            result.setStatus(200);
            result.setMsg("登录成功");
        }else {
            result.setStatus(404);
            if (psword == "" || psword == null){
                result.setMsg("没有该账号");
            }else {
                result.setMsg("密码错误");
            }
        }
        return result;
    }

//    注册
//    public Result signin(int userid,String username,String userimg,String userpsword){
    public Result signin(String username,String userpsword){
        Result result = new Result();
        long nowDate = new Date().getTime();   //利用时间戳生成随机数
        int userid= new Long(nowDate).intValue();

        if(userDao.selectuserpsword(userid) != null) {
            result.setStatus(404);
            result.setMsg("该账号已存在");
        }else {
//            this.userDao.insertuser(userid,username,userimg,userpsword);
            this.userDao.insertuser(userid,username,userpsword);
            result.setStatus(200);
            result.setMsg(String.valueOf(userid));
        }
        return result;
    }

    //查看用户信息
    public Result selectuserinfo(int userid){
//        Result result = new Result();
//        result.setStatus(200);
//        result.setObj(this.userDao.selectuserinfo(userid));
        String key = "userinfo_"+userid;

        ValueOperations<String,Result> operations = redisTemplate.opsForValue();

        //判断redis中是否有键为key的缓存
        boolean hasKey = redisTemplate.hasKey(key);

        if (hasKey){
            Result result = operations.get(key);
            System.out.println("从缓存查询到"+key);
            return result;
        } else {
            Result result = new Result();
            result.setStatus(200);
            result.setObj(this.userDao.selectuserinfo(userid));
            System.out.println("从数据库查询到"+key);

            // 写入缓存
            operations.set(key,result,5, TimeUnit.HOURS);  //五小时
            return result;
        }

    }

    //查看用户好友
    public Result selectuserfriend(int userid){
        Result result = new Result();
        result.setStatus(200);
//        List<String> userfriendList = new ArrayList(userDao.selectuserfriend(userid));
//        String userfriend = String.join(",",  userfriendList.toArray(new String[userfriendList.size()]));
//        result.setMsg(userfriend);
        result.setObj(this.userDao.selectuserfriend(userid));
        return result;
    }

    //查看用户聊天室
    public Result selectuserroom(int userid){
        Result result = new Result();
        result.setStatus(200);
        result.setObj(this.userDao.selectuserroom(userid));
        return result;
    }

    //修改用户头像
    public Result updateuserimg(int userid,String img){
        Result result = new Result();
        this.userDao.updateuserimg(img,userid);
        result.setStatus(200);
        result.setMsg("修改成功");
        return result;
    }

//    修改用户名
    public Result updateusername(int userid,String username){
        Result result = new Result();
        this.userDao.updateusername(username,userid);
        result.setStatus(200);
        result.setMsg("修改成功");
        return result;
    }

//    修改用户密码
    public Result updateuserpsword(int userid,String userpsword,String newuserpsword){
        Result result = new Result();
        if (userpsword.equals(this.userDao.selectuserpsword(userid))){
            this.userDao.updateuserpsword(newuserpsword,userid);
            result.setStatus(200);
            result.setMsg("修改成功");
        }else {
            result.setStatus(500);
            result.setMsg("修改失败");
        }
        return result;
    }

//    发送消息
    public Result send(Date date,int userid, int toid, String message){
    Result result = new Result();
    this.messageDao.insertmessage(date,userid,toid,message);
    result.setStatus(200);
    result.setMsg("发送成功");
    return result;
}

//    查询历史消息
    public Result searchinfo(int userid){
        Result result = new Result();
        result.setStatus(200);
        result.setObj(this.messageDao.selectmessage(userid));

        return result;
    }

//    添加好友
    public Result addfriend(int myid,int userid){
        Result result = new Result();
        this.myFriendDao.addfriend(myid,userid);
        result.setStatus(200);
        result.setMsg("添加成功");

        return result;
    }

//    删除好友
    public Result deletefriend(int myid,int userid){
        Result result = new Result();
        this.myFriendDao.deletefriend(myid,userid);
        result.setStatus(200);
        result.setMsg("删除成功");

        return result;
    }

//    创建聊天室
    public Result createroom(int id,int roomid,String roomname,String date){
        Result result = new Result();
        this.roomDao.createroom(id,roomid,roomname,date);
        result.setStatus(200);
        result.setMsg("创建成功");
        return result;
    }

//    修改聊天室名称
    public Result updateroomname(int id,String newroomname,int roomid){
        Result result = new Result();
        this.roomDao.updateroomname(id,newroomname,roomid);
        result.setStatus(200);
        result.setMsg("修改成功");

        return result;
    }

//    添加聊天室成员
    public Result addroommembers(int roomid,int memberid){
        Result result = new Result();
        this.roomDao.addroommembers(roomid,memberid);
        result.setStatus(200);
        result.setMsg("添加成功");
        return result;
    }

//    查看聊天室成员
    public Result selectroommembers(int roomid){
        Result result = new Result();
//        List<String> roommembersList = new ArrayList(roomDao.selectroommembers(id,roomid));
//        String roommembers = String.join(",",  roommembersList.toArray(new String[roommembersList.size()]));
        result.setStatus(200);
        result.setObj(roomDao.selectroommembers(roomid));
        return result;
    }

//    删除并退出聊天室
    public Result deleteroom(int id, int roomid){
        Result result = new Result();
        this.roomDao.deleteroom(id, roomid);
        result.setStatus(200);
        result.setMsg("退出成功");
        return result;
    }
}
