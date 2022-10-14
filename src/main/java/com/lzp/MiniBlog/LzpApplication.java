package com.lzp.MiniBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//@EnableTransactionManagement用于开启事务配置类
@EnableTransactionManagement
@SpringBootApplication
public class LzpApplication {
	public static void main(String[] args) {
		SpringApplication.run(LzpApplication.class, args);
	}
}
