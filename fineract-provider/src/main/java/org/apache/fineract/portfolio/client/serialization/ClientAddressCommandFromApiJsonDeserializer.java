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
package org.apache.fineract.portfolio.client.serialization;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.client.command.ClientAddressCommand;
import org.apache.fineract.portfolio.client.command.ClientIdentifierCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


@Component
public class ClientAddressCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<ClientAddressCommand>{
	
	/**
	 * The Parmeter Supported for this command
	 */
	private final Set<String> supportedParameters = new HashSet<>(Arrays.asList("addressTypeId", "address_line", "address_line_two", "landmark", "city", "pincode", "isBoth", "stateTypeId"));
	
	private final FromJsonHelper fromApiJsonHelper;
	
	@Autowired
	public ClientAddressCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper){
		
		this.fromApiJsonHelper = fromApiJsonHelper;
		
	}
	
	@Override
	public ClientAddressCommand commandFromApiJson(final String json){
		
		if (StringUtils.isBlank(json)){throw new InvalidJsonException();}
		final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);
        final JsonElement element = this.fromApiJsonHelper.parse(json);
        final Long addressTypeId = this.fromApiJsonHelper.extractLongNamed("addressTypeId", element);
        final String address_line = this.fromApiJsonHelper.extractStringNamed("address_line", element);
        final String address_line_two = this.fromApiJsonHelper.extractStringNamed("address_line_two", element);
        final String landmark = this.fromApiJsonHelper.extractStringNamed("landmark", element);
        final String city = this.fromApiJsonHelper.extractStringNamed("city",element);
        final String pincode = this.fromApiJsonHelper.extractStringNamed("pincode", element);
        final Boolean isBoth = this.fromApiJsonHelper.extractBooleanNamed("isBoth", element);
        final Long stateTypeId = this.fromApiJsonHelper.extractLongNamed("stateTypeId", element);
        
        	
        	return new ClientAddressCommand(addressTypeId,address_line, address_line_two, landmark,city,pincode,isBoth,stateTypeId);
    }
		
	}