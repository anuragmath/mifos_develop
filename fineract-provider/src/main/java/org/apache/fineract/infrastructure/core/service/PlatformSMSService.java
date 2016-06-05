package org.apache.fineract.infrastructure.core.service;

import org.apache.fineract.infrastructure.sms.domain.SmsMessage;

public interface PlatformSMSService {
	
	void sendMessage(final SmsMessage message);

}
