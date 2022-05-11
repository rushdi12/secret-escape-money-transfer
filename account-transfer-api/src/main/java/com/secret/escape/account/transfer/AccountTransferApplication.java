package com.secret.escape.account.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AccountTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountTransferApplication.class, args);
	}

}
