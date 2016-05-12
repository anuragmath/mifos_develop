/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.client.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;

/**
 * 
 * @author saranshsharma
 * Checking the duplication of Address Type and returns exception
 */

public class DuplicateClientAddressException extends AbstractPlatformDomainRuleException {
	
	private Long addressTypeId;
	private String address_line;
	//private Long stateTypeId;
	//private String city;
	//private String pincode;
	//private Boolean isBoth;
	private final String addressType;
	//private final String stateType;
	
	public DuplicateClientAddressException(final String addressType){
		super("error.msg.clientaddress.type.duplicate", "Client Address of Type " + addressType
				+ "is already present", addressType);
		this.addressType = addressType;

	}
	
	
	public DuplicateClientAddressException(final Long addressTypeId, final String addressType, final String address_line){
		
		super("error.msg.clientaddress.address_line.duplicate", "Client Address of Type " + addressType + "with value of " 
				  + "already exists", addressType);
		this.addressTypeId = addressTypeId;
		this.addressType = addressType;
		//this.stateTypeId = stateTypeId;
		this.address_line = address_line;
		//this.stateType = stateType;
		
	}
	
	public DuplicateClientAddressException(final String clientName, final String officeName, final String addressType, 
				final String address_line
                      ) {
        super("error.msg.clientaddress.address_line.duplicate", "Client " + clientName + "under " + officeName + " Branch already has a "
                + addressType + " with unique address "  + "with unique state" , clientName, officeName, addressType);
        this.addressType = addressType;
        this.address_line = address_line;
       // this.stateType = stateType;
    }
	
	
	public Long getAddressTypeId(){
		return this.addressTypeId;
	}
	
 //		return this.stateTypeId;
//	}
//	public String getCity(){
//		return this.city;
//	}
//	public String getPinCode(){
//		return this.pincode;
//	}
	
	public String getAddress_Line(){
		return this.address_line;
	}
	
	public String getAddressType(){
		return this.addressType;
	}
	
//	public String getStateType(){
	//	return this.stateType;
//	}
	
//	public Boolean getIsBoth(){
//		return this.isBoth;
//	}
//	
	
	

}