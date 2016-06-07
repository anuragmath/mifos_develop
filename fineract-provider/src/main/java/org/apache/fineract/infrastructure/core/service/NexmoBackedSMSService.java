package org.apache.fineract.infrastructure.core.service;


import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;

import org.apache.fineract.infrastructure.configuration.data.NexmoCredentialsData;
import org.apache.fineract.infrastructure.configuration.service.ExternalServicesPropertiesReadPlatformService;
import org.apache.fineract.infrastructure.sms.domain.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NexmoBackedSMSService implements PlatformSMSService {
	
	private final ExternalServicesPropertiesReadPlatformService externalService;
	
	@Autowired
	public  NexmoBackedSMSService(final ExternalServicesPropertiesReadPlatformService externalService) {
		// TODO Auto-generated constructor stub
		this.externalService = externalService;
}

	@Override
	public void sendMessage(final SmsMessage smsMessage) {
		// TODO Auto-generated method stub
		
		/*
		 * NEXMO SMS Here we are fetching the NEXMO SMS API credentials data , which will allow us to send SMS 
		 */
		
		
		final NexmoCredentialsData nexmoCredentials = this.externalService.getNexmoCredentials();
		
		NexmoSmsClient client = null;
		
		try{
			client = new NexmoSmsClient(nexmoCredentials.getApiKey(), nexmoCredentials.getApiSecret());
			
		}catch (Exception e){
			System.err.println("failed to insatnciate a Nexmo Client");
			e.printStackTrace();
			throw new RuntimeException("Failed to instanciate a new client");
		}
		
		TextMessage message = new TextMessage(nexmoCredentials.getSmsFrom(), smsMessage.getMobileNumber(), smsMessage.toString());
		
		SmsSubmissionResult [] results = null;
		
		try {
			results = client.submitMessage(message);
			
		}catch (Exception e){
			System.err.println("Failed to communicate with nexmo client");
			e.printStackTrace();
			throw new RuntimeException("Failed to communicate with nexmo client");
		}
		
		 System.out.println("... Message submitted in [ " + results.length + " ] parts");
	        for (int i=0;i<results.length;i++) {
	            System.out.println("--------- part [ " + (i + 1) + " ] ------------");
	            System.out.println("Status [ " + results[i].getStatus() + " ] ...");
	            if (results[i].getStatus() == SmsSubmissionResult.STATUS_OK)
	                System.out.println("SUCCESS");
	            else if (results[i].getTemporaryError())
	                System.out.println("TEMPORARY FAILURE - PLEASE RETRY");
	            else
	                System.out.println("SUBMISSION FAILED!");
	            System.out.println("Message-Id [ " + results[i].getMessageId() + " ] ...");
	            System.out.println("Error-Text [ " + results[i].getErrorText() + " ] ...");

	            if (results[i].getMessagePrice() != null)
	                System.out.println("Message-Price [ " + results[i].getMessagePrice() + " ] ...");
	            if (results[i].getRemainingBalance() != null)
	                System.out.println("Remaining-Balance [ " + results[i].getRemainingBalance() + " ] ...");
	        }
		
	
		
	}
	
	
	

}
