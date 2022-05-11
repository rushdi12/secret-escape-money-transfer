package com.secret.escape.account.transfer.service;

import com.secret.escape.account.transfer.dto.EmailEventDto;

public interface EmailService {

    public void sendEmail(EmailEventDto email);
    	
}
