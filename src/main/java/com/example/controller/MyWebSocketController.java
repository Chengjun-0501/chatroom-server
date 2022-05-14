package com.example.controller;

import com.example.bean.Result;
import com.example.config.MessageCenter;
import com.example.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.bean.MyFriend;


@Component
@ServerEndpoint(value = "/websocket/{myid}")

public class MyWebSocketController {
//    private static Logger logger = Logger.getLogger(MyWebSocketConfig.class);

    //用来存放每个客户端对应的WebSocketTest对象，适用于同时与多个客户端通信
    public static CopyOnWriteArraySet<MyWebSocketController> webSocketSet = new CopyOnWriteArraySet<MyWebSocketController>();
    //若要实现服务端与指定客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<String, MyWebSocketController> webSocketMap = new ConcurrentHashMap<String, MyWebSocketController>();

    //与某个客户端的连接会话，通过它实现定向推送(只推送给某个用户)
    private Session session;

    //把登录用户的id放在url请求中
    private String myid = "";

//    多线程推送报错解决方法
//    synchronized (session) {
//        session.getBasicRemote().sendText(message);
//    }

    /**
     * 为websocket注入实例
     */
    private static UserService userService;
    @Autowired
    public void setChatService(UserService userService) {
        MyWebSocketController.userService = userService;
    }

    /**
     * 建立连接成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "myid") String param,Session session) {
        myid = param;
        System.out.println(myid);
        this.session = session;
        webSocketSet.add(this);  // 添加到set中
        webSocketMap.put(myid,this);    // 添加到map中

/*
        // 1.把每个客户端的session都保存起来，之后转发消息到所有客户端要用
//        MessageCenter.addOnlineUser(userId,session);
        MessageCenter.getInstance().addOnlineUser(userId,session);
        // 2.查询本客户端（用户）上次登录前的消息（数据库查）
        List<Message> list = MessageDao.queryByLastLogout(userId);
        // 3.发送当前用户在上次登录后的消息
        for (Message m : list) {
            session.getBasicRemote().sendText(Util.serialize(m));
        }
*/

        System.out.println("新人 "+myid+" 加入，当前在线人数为："  + webSocketMap.size());

    }

    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose(Session closeSession){
        webSocketMap.remove(myid);
        webSocketSet.remove(this);
        System.out.println("有人离开，当前在线人数为：" + webSocketMap.size());

        /*
            public void onClose(@PathParam("userId") Integer userId) {
//1.本客户端关闭连接，要在之前保存的session集合中，删除
//        MessageCenter.delOnlineUser(userId);
        MessageCenter.getInstance().delOnlineUser(userId);
        //2.建立连接要获取用户上次登录以后的消息，所以关闭长连接就是代表用户退出
        //更新用户的上次登录时间
        int n = UserDao.updateLastLogout(userId);
         */

    }




//    @ResponseBody
//    @PostMapping("/main/online")
//    public String updateUserPsword(@RequestBody JSONObject obj){
//        String id = obj.getString("id");
//        if (webSocketMap.get(id) != null){
//            return "在线";
//        } else {
//            return "离线";
//        }
//    }

    /**
     *  收到客户端消息调用的方法
     */
    @OnMessage
    public void onMessage(String str) throws Exception{
        JSONObject obj = JSONObject.fromObject(str);
        System.out.println(obj);
//        String id = obj.getString("myid");
        String toid = obj.getString("toid");
        String roomid = obj.getString("roomid");
        String type = obj.getString("type");
        String date = obj.getString("date");
        String msg = obj.getString("msg");
        try {
            if (type.equals("friend") && webSocketMap.get(toid) != null) {
                webSocketMap.get(toid).sendMessage("用户（"+myid+"） : "+msg);
            } else if (roomid != null && roomid != "") {
                Result re = userService.selectroommembers(Integer.valueOf(roomid));
                ArrayList<MyFriend> member = (ArrayList<MyFriend>) re.getObj();   //强转类型
                for (MyFriend me :member)
                {
                    String memberid = String.valueOf(me.getId());
                    if (webSocketMap.get(memberid) != null){
                        webSocketMap.get(memberid).sendMessage("群聊（"+roomid+"） 》 "+"用户（"+myid+"） : "+msg);
                    }
                }
            } else {
                System.out.println("该用户当前未在线");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//    @OnMessage
//    public void onMessage(Session session, String message) {
//        // 1.遍历保存的所有session，每个都发送消息
////        MessageCenter.sendMessage(message);
//        MessageCenter.getInstance().addMessage(message);
//        // 2.消息还要保存在数据库，
//        // （1）反序列化json字符串为message对象
//        Message msg = Util.deserialize(message, Message.class);
//        // （2）message设置接收消息的时间
////        msg.setSendTime(new Date());
//        // （3）插入数据库
//        int n = MessageDao.insert(msg);
//
//        System.out.println("接收到的消息：" + message);
//    }


    @OnError
    public void onError(@PathParam("userId") Integer userId, Throwable throwable) {
        System.out.println("出错了");
        //和关闭连接的操作一样
//        MessageCenter.delOnlineUser(userId);
        MessageCenter.getInstance().delOnlineUser(userId);
        throwable.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
