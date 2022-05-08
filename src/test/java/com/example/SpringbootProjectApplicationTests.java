package com.example;

import cn.dev33.satoken.secure.SaSecureUtil;
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
        String publicKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ3aGMnee0Lj62QdwCRd9EIVhgrvBIz57WSHO8FNpol7u477qgAcC/qsIA6g/yMjYY8v1zxoMiTDunvVasLLdYxkiJPzd4Sq8Hg2TC2v1rCrmBctRWDtVEXn7JsuMtHhBVO1zXAqnSgwohbneLKnUjq90K+UnPidqt8XaF1uA29TAgMBAAECgYBQampUdQYgdpJVp55aKI8mF2b5LPkJhM1+eDJl8NQVmDZdyjG56o/jvUp7iWNRGFY8JugkuwRInHnlWj0QTaxs8r8koI6AQIVKlM5mEvkkYV7XvCDRGyISwm6POSXONWLiYSPIyevSiw/dpzFjg/TPETB+yb0o38CvLHnO5kvvkQJBANrZKfxkTRPhRi7peSgiWwhGDjYF7fCVeR540mvB598Ab1WSl76GSLvfLjJJPiWTmlN1cfAuWvE7hUTbQElpe/kCQQC4ph4sNAlDDlfx1PooP4XTQ7vp7MZT0V058Scscjj3yizL9Yuxut9vGXiq4yv9vF1O4vVdfckWqItijMgAIqCrAkEAiKhrC2wmpW9awBiSy3kIl1YHsbxqJH1yMJEJ9LgU7q/61djAYPzBlm97DXOnFxfgmpUQHQcVSuX8Bk9TCYSRIQJAHu1sBllc58diQZY7pDwnjSA+PEs1WNqrc8YJmq8zDYXmhO8Gy+kR/YjVpkHQn3pKYqkvEc68vLfUSNhts2AZawJBAIP53pRnPKbQDm6eQTdXik01BNXYS2JtWRSxNIJB2RqCT03AcOANwhxVRcetJnTOpvoO13PjuqKliXn6bzzMKf8=";
        System.out.println(SaSecureUtil.aesDecrypt(publicKey,"2eaSGNQweTm56VLs3XPYBQ=="));
    }
}


