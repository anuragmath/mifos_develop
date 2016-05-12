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
package org.apache.fineract.portfolio.client.data;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class ClientAddressData {
	
	private final Long id;
	private final Long clientId;
	private final CodeValueData addressType;
	private final String address_line;
	private final String address_line_two;
	private final String landmark;
	private final String city;
	private final String pincode;
	private final Boolean isBoth;
	private final CodeValueData stateType;
	
	@SuppressWarnings("unused")
	private final Collection<CodeValueData>allowedAddressTypes;
	private final Collection<CodeValueData>allowedStateTypes;
	
	public static ClientAddressData singleItem(final Long id, final Long clientId, final CodeValueData addressType,
				final String address_line,
				final String address_line_two,final String landmark,
				final String city,
				final String pincode,
				final Boolean isBoth,
				final CodeValueData stateType){
		return new ClientAddressData (id, clientId, addressType, address_line, address_line_two, landmark, city,pincode,isBoth, stateType,null, null);
		
		
	}
	
	public static ClientAddressData template(final Collection<CodeValueData> codeValues ,final Collection<CodeValueData> stateValues){
		
		return new ClientAddressData(null, null, null, null ,null ,null, null, null,null,null, codeValues, stateValues);
	}
	
	public static ClientAddressData template (final ClientAddressData data, final Collection<CodeValueData> codeValues, final Collection<CodeValueData> stateValues){
		
		return new ClientAddressData(data.id, data.clientId, data.addressType, data.address_line, data.address_line_two,data.landmark,data.city,data.pincode,data.isBoth,data.stateType,codeValues,stateValues);
		
	}
	
	public ClientAddressData(final Long id, final Long clientId, final CodeValueData addressType,final String address_line ,final String address_line_two,
    		final String landmark, final String city, 
    		final String pincode,
    		final Boolean isBoth,
    		final CodeValueData stateType,
            final Collection<CodeValueData> allowedAddressTypes, final Collection<CodeValueData>allowedStateTypes) {
        this.id = id;
        this.clientId = clientId;
        this.addressType = addressType;
        this.address_line = address_line;
        this.address_line_two = address_line_two;
        this.landmark = landmark;
        this.city = city;
        this.pincode = pincode;
        this.isBoth = isBoth;
        this.stateType = stateType;
        this.allowedAddressTypes = allowedAddressTypes;
        this.allowedStateTypes = allowedStateTypes;
    }

	public Collection<CodeValueData> getallowedStateTypes() {
		return allowedStateTypes;
	}

	
	
}