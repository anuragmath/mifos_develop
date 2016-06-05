package org.apache.fineract.infrastructure.configuration.data;

public class NexmoCredentialsData {
	
	private final String apiKey;
	
	private final String apiSecret;
	
	private final String smsFrom;
	
	public NexmoCredentialsData(final String apiKey, final String apiSecret, final String smsFrom){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.smsFrom = smsFrom;
	}
	
	public String getApiKey(){
		return this.apiKey;
	}
	
	public String getApiSecret(){
		return this.apiSecret;
	}
	
	public String getSmsFrom(){
		return this.smsFrom;
	}

}
