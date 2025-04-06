package com.phamvantoan.webBanSachBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebBanSachBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebBanSachBackendApplication.class, args);
	}

}
