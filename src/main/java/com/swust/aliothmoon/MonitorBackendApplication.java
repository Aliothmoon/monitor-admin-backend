package com.swust.aliothmoon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.swust.aliothmoon.mapper")
public class MonitorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorBackendApplication.class, args);
    }

}
