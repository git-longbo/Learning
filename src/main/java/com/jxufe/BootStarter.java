package com.jxufe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.jxufe.demo")
@MapperScan("com.jxufe.demo.mapper")
@EnableAsync
public class BootStarter {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootStarter.class, args);
    }
}
