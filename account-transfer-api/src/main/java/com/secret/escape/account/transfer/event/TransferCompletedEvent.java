package com.secret.escape.account.transfer.event;

import java.util.List;

import com.secret.escape.account.transfer.dto.EmailEventDto;

public class TransferCompletedEvent {
	
	private final List<EmailEventDto> emailEvent;

	  public TransferCompletedEvent(List<EmailEventDto> emailEvent) {
	    this.emailEvent = emailEvent;
	  }

	  public List<EmailEventDto> getEmailEvent() {
	    return emailEvent;
	  }

}
