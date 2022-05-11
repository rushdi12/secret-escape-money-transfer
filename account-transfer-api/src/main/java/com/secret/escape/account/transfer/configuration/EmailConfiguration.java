package com.secret.escape.account.transfer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfiguration {
	
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setText("Payment Notification\n\nAccount Number: %s\nAmount: %s\nbalance: %s\n");
	    
	    return message;
	}
}
