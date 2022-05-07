package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.example.bean.Result;
import com.example.dao.MessageDao;
import com.example.dao.UserDao;
import com.example.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//origins:允许可访问的域列表;   maxAge:准备响应前的缓存持续的最大时间（以秒为单位）
@CrossOrigin//(origins = "http://localhost:8080/", maxAge = 3600)
@Controller
//@RestController   //相比Controller，@RestController不会返回jsp界面而只会返回return的内容
public class MainController {
    @Autowired
    UserDao userDao;

    @Autowired
    MessageDao messageDao;

    @Autowired
    UserService userService;

    private static String publicKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ3aGMnee0Lj62QdwCRd9EIVhgrvBIz57WSHO8FNpol7u477qgAcC/qsIA6g/yMjYY8v1zxoMiTDunvVasLLdYxkiJPzd4Sq8Hg2TC2v1rCrmBctRWDtVEXn7JsuMtHhBVO1zXAqnSgwohbneLKnUjq90K+UnPidqt8XaF1uA29TAgMBAAECgYBQampUdQYgdpJVp55aKI8mF2b5LPkJhM1+eDJl8NQVmDZdyjG56o/jvUp7iWNRGFY8JugkuwRInHnlWj0QTaxs8r8koI6AQIVKlM5mEvkkYV7XvCDRGyISwm6POSXONWLiYSPIyevSiw/dpzFjg/TPETB+yb0o38CvLHnO5kvvkQJBANrZKfxkTRPhRi7peSgiWwhGDjYF7fCVeR540mvB598Ab1WSl76GSLvfLjJJPiWTmlN1cfAuWvE7hUTbQElpe/kCQQC4ph4sNAlDDlfx1PooP4XTQ7vp7MZT0V058Scscjj3yizL9Yuxut9vGXiq4yv9vF1O4vVdfckWqItijMgAIqCrAkEAiKhrC2wmpW9awBiSy3kIl1YHsbxqJH1yMJEJ9LgU7q/61djAYPzBlm97DXOnFxfgmpUQHQcVSuX8Bk9TCYSRIQJAHu1sBllc58diQZY7pDwnjSA+PEs1WNqrc8YJmq8zDYXmhO8Gy+kR/YjVpkHQn3pKYqkvEc68vLfUSNhts2AZawJBAIP53pRnPKbQDm6eQTdXik01BNXYS2JtWRSxNIJB2RqCT03AcOANwhxVRcetJnTOpvoO13PjuqKliXn6bzzMKf8=";

    public HttpSession httpSession;
//    线程安全集合
    public static ConcurrentHashMap<Integer, HttpSession> httpSessionMap = new ConcurrentHashMap<Integer,HttpSession>();

    @ResponseBody
    @PostMapping("/login/in")
    public Result login(@RequestBody JSONObject obj, HttpServletRequest request){
//        Logger log = Logger.getLogger("user-login");
//        log.setLevel(Level.INFO);
        int userid = obj.getInt("userid");   //获取登录信息
        StpUtil.login(userid);
        // AES
        String psword = SaSecureUtil.aesEncrypt(publicKey, obj.getString("userpsword"));
//        System.out.println(psword);
//        this.httpSession = request.getSession();   //获取当前session，如果没有则创建
//        this.httpSession.setAttribute("userid",userid);   //在session中存储用户id
//        httpSessionMap.put(userid, this.httpSession);   //将用户id及对应的session存入集合
//        log.info(obj+"  -------  "+this.httpSession.getAttribute("userid"));
        return userService.login(userid,psword);
    }

    @ResponseBody
    @PostMapping("/login/out")
    public void loginout(@RequestBody JSONObject obj, HttpServletRequest request){
        Logger log = Logger.getLogger("user-login-out");
        log.setLevel(Level.INFO);
//        int userid = obj.getInt("userid");   //获取用户信息
//        this.httpSession = request.getSession(false);   //变量false代表若无对应的session，不再重新创建
//        httpSessionMap.remove(this.httpSession.getAttribute("userid"));   //从集合中删除这个session的数据
//        invalidate();   //该方法是清空内存中的session
//        log.info(obj+"  -------  "+this.httpSession.getAttribute("userid")+">>>>>"+httpSessionMap);
        log.info(obj+"  -------  "+StpUtil.getLoginId());
        StpUtil.logout();
    }

    @ResponseBody
    @PostMapping("/signin/in")
//    public Result sign(HttpServletRequest request) {
    public Result sign(@RequestBody JSONObject obj) {
        Logger log = Logger.getLogger("user-signin");
        log.setLevel(Level.INFO);
        String username = obj.getString("username");
        String psword = SaSecureUtil.aesEncrypt(publicKey, obj.getString("psword"));
        log.info(obj+username+"  -------  "+psword);
        return userService.signin(username, psword);
    }

//    @ResponseBody
//    @RequestMapping("/main/send")
//    public Result sendMessage(int userid,int toid,String msg){
//        Date date = new Date();
//        return userService.send(date,userid,toid,msg);
//    }

    @ResponseBody
    @RequestMapping("/main/status")
    public String main(HttpServletRequest request) {
//        Logger log = Logger.getLogger("user-main");
//        log.setLevel(Level.INFO);
//        log.info("当前新登入用户id：" +session.getAttribute("userid"));      // 获取Session属性
//        this.httpSession = request.getSession(false);       // 获取Session对象
//        System.out.println(String.valueOf(this.httpSession.getAttribute("userid")));
//        return String.valueOf(this.httpSession.getAttribute("userid"));
        return StpUtil.getLoginIdAsString();
    }

    @ResponseBody
    @RequestMapping("/main/info")
    public Result searchinfo(HttpServletRequest request) {
        StpUtil.checkLogin();
        Logger log = Logger.getLogger("user-main-info");
        log.setLevel(Level.INFO);
        Result result = new Result();
        if (request.getSession(false) != null){
            this.httpSession = request.getSession(false);       // 获取Session对象
            int userid = ((Integer) this.httpSession.getAttribute("userid")).intValue();
//            System.out.println(userid+"    "+this.httpSession.getAttribute("userid"));
            result.setObj(messageDao.selectmessage(userid));
            log.info("查询操作》用户id：" +userid+"   查询结果："+messageDao.selectmessage(userid));      // 获取Session属性
        } else {
            result.setStatus(401);
            result.setMsg("登录过期，需要重新登录");
        }
        return result;
    }

    @Cacheable(cacheNames = "userService.selectuserinfo(id)", unless = "#result==null")  //自动根据方法生成缓存
    @ResponseBody
    @PostMapping("/user/info")
    public Result getUserInfo(@RequestBody JSONObject obj){
//        try {
//            StpUtil.checkLogin();
////            String id = obj.getString("id");
////            System.out.println("user-getinfo    "+id);
//            int id = StpUtil.getLoginIdAsInt();
//            return userService.selectuserinfo(id);
//        }catch (Exception e){
//            System.out.println(StpUtil.getLoginId()+"登录失败");
//        }
//        return new Result();
//        StpUtil.checkLogin();
        int id = StpUtil.getLoginIdAsInt();
        return userService.selectuserinfo(id);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/name")
    public Result updateUserName(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String name = obj.getString("name");
        System.out.println("user-updatename    "+id+"   "+name);
        return userService.updateusername(id,name);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/psword")
    public Result updateUserPsword(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String psword = SaSecureUtil.aesEncrypt(publicKey, obj.getString("psword"));
        String newpsword = SaSecureUtil.aesEncrypt(publicKey, obj.getString("newpsword"));
        System.out.println("user-updatename    "+id+"   "+newpsword);
        return userService.updateuserpsword(id,psword,newpsword);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/friendlist")
    public Result getUserFriend(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        System.out.println("user-getfriendlist    "+id);
        return userService.selectuserfriend(Integer.valueOf(id));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/addfriend")
    public Result addFriend(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String friendid = obj.getString("friendid");
        System.out.println("user-addfriend    "+id+"   "+friendid);
        return userService.addfriend(id,Integer.valueOf(friendid));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/dropfriend")
    public Result deleteFriend(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String friendid = obj.getString("friendid");
        System.out.println("user-deletefriend    "+id+"   "+friendid);
        return userService.deletefriend(id,Integer.valueOf(friendid));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/roomlist")
    public Result getUserRoom(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        System.out.println("user-roomlist    "+id);
        return userService.selectuserroom(id);
    }

    @ResponseBody
    @PostMapping("/user/roommember")
    public Result getUserRoomMembers(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String roomid = obj.getString("roomid");
        System.out.println("user-roommember    "+id+"   "+roomid);
        return userService.selectroommembers(Integer.valueOf(roomid));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/addroom")
    public Result addRoom(@RequestBody JSONObject obj){
        StpUtil.checkLogin();
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String roomid = obj.getString("roomid");
        System.out.println("user-addroom    "+id+"   "+roomid);
        return userService.addroommembers(Integer.valueOf(roomid),id);
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/deleteroom")
    public Result deleteRoom(@RequestBody JSONObject obj){
        StpUtil.checkLogin();
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String roomid = obj.getString("roomid");
        System.out.println("user-deleteroom    "+id+"   "+roomid);
        return userService.deleteroom(id,Integer.valueOf(roomid));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/roomname")
    public Result updateRoomName(@RequestBody JSONObject obj){
        StpUtil.checkLogin();
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        String roomname = obj.getString("name");
        String roomid = obj.getString("roomid");
        System.out.println("user-updateroomname    "+id+"   "+roomname+"   "+roomid);
        return userService.updateroomname(id,roomname,Integer.valueOf(roomid));
    }

    @SaCheckLogin
    @ResponseBody
    @PostMapping("/user/createroom")
    public Result createRoom(@RequestBody JSONObject obj){
        int id = StpUtil.getLoginIdAsInt();
//        String id = obj.getString("id");
        long nowDate = new Date().getTime();   //利用时间戳生成随机数
        int roomId= new Long(nowDate).intValue();
        String roomname = obj.getString("name");
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   //格式化日期
        System.out.println("user-createroom    "+id);
        return userService.createroom(id,roomId,roomname,ft.format(dNow));
    }
}
