package com.asd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailWorkerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(EmailWorkerApplication.class, args);
		Thread.currentThread().join();
	}

}
