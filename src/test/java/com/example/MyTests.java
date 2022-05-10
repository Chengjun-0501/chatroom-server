package com.example;

import cn.dev33.satoken.secure.SaSecureUtil;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTests {

    @Test
    public void s() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(df.format(localDateTime));
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   //格式化日期
        System.out.println(ft.format(dNow));
    }
}


