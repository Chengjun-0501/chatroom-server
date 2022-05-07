package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest
public class SpringbootProjectApplicationTests {

    @Test
    void s() {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
        map.put("2", "s");
        map.put("1", "d");

        System.out.println(map);
    }
}


