package org.apache.fineract.infrastructure.sms.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class SmsNotSentException extends AbstractPlatformResourceNotFoundException{
	
	public SmsNotSentException(){
		super("error.msg.sms.not.sent", "SMS with identifier`" , "`could not be sent something is wrong inside the data or the code which sends the sms " );
	}

}
