package com.secret.escape.account.transfer.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.secret.escape.account.transfer.dto.EmailEventDto;
import com.secret.escape.account.transfer.event.TransferCompletedEvent;
import com.secret.escape.account.transfer.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransferFundsEventListener {
	
	@Autowired
	private EmailService emailService;
	
	@TransactionalEventListener
	@Async
    public void transferCompleteEvent(TransferCompletedEvent tansferCompletedEvent) {
	 
		List<EmailEventDto> emailEvent = tansferCompletedEvent.getEmailEvent();
		
		emailEvent.forEach(e -> {
			emailService.sendEmail(e);
		});
		
		log.debug("Sending email");
    }
}
