package com.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class HbaseSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbaseSpringbootApplication.class, args);
		//System.out.println("hhh\n");
		HBaseConnection.HbaseGetConnection();
		HBaseConnection.getData_World("000.png");
	}
}
