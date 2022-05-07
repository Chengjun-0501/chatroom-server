package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.Serializable;

//@AllArgsConstructor 注解作用在类上，使用后为类生成一个全参构造函数（含有已申明的所有属性参数）
//@NoArgsConstructor 注解作用在类上，使用后为类生成一个无参构造函数
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component

//定义报错日志格式
public class AuthorizationException implements Serializable {
//    serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。
    private static final long serialVersionUID = 1L;

    private int code;
    private String status;
    private String uri;
    private String message;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"message\":\"" + message + "\"," +
                "\"uri\":\"" + uri + "\"" +
                "}";
    }
}