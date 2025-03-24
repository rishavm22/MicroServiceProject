package com.rishav.doc_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocManagerApplication.class, args);
	}

}
