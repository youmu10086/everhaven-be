package com.cyf.everhavenbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.cyf.everhavenbe.mapper")  // ← 必须加，确保能扫到 UserMapper
public class EverhavenBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EverhavenBeApplication.class, args);

    }
}
