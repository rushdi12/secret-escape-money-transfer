package com.secret.escape.account.transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.secret.escape.account.transfer.dto.EmailEventDto;
import com.secret.escape.account.transfer.util.Convert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	public SimpleMailMessage template;

	@Override
	public void sendEmail(EmailEventDto email) {

		try {

			String accountNumber = email.getAccountNumber();
			String amount = Convert.formatMinorDenominationAmount(String.valueOf(email.getAmount()), 2, false, "\u00A3");
			String balance = Convert.formatMinorDenominationAmount(String.valueOf(email.getBalance()), 2, false, "\u00A3");
			
			String text = String.format(template.getText(), accountNumber, amount, balance);

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("noreply@secret-escape.com");
			message.setTo(email.getEmail());
			message.setSubject("Payment Notification");
			message.setText(text);
			emailSender.send(message);

		} catch (Exception ex) {
			log.error("Error sending email", ex);

		}
	}

}
