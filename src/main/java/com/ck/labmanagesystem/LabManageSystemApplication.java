package com.ck.labmanagesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.ck.labmanagesystem.mapper")
public class LabManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabManageSystemApplication.class, args);
    }

}
