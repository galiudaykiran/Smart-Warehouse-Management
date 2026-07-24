package com.smart_warehouse_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartWarehouseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartWarehouseManagementApplication.class, args);
	}

}
