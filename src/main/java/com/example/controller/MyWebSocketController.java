package com.example.controller;

import com.example.bean.Result;
import com.example.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.example.bean.MyFriend;

@Controller
@Component
@ServerEndpoint(value = "/websocket/{myid}")
//@ServerEndpoint(value = "/websocket")

public class MyWebSocketController {
//    private static Logger logger = Logger.getLogger(MyWebSocketConfig.class);
    //线程安全的静态变量，表示在线连接数
    private static volatile int onlineCount = 0;

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
     * 建立连接成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "myid") String param,Session session) {
//    public void onOpen(Session session) {
        myid = param;
//        myid = StpUtil.getLoginIdAsString();
        System.out.println(myid);
        this.session = session;
        webSocketSet.add(this);  // 添加到set中
        webSocketMap.put(myid,this);    // 添加到map中
//        addOnlineCount();    // 添加在线人数
//        System.out.println("新人 "+myid+" 加入，当前在线人数为："  + getOnlineCount());
        System.out.println("新人 "+myid+" 加入，当前在线人数为："  + webSocketMap.size());

    }

    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose(Session closeSession){
        webSocketMap.remove(myid);
        webSocketSet.remove(this);
//        subOnlineCount();
//        System.out.println("有人离开，当前在线人数为：" + getOnlineCount());
        System.out.println("有人离开，当前在线人数为：" + webSocketMap.size());

    }


    private static UserService userService;
//    为websocket注入实例
    @Autowired
    public void setChatService(UserService userService) {
        MyWebSocketController.userService = userService;
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
//
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

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
