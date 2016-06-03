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

package org.apache.fineract.portfolio.loanaccount.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryRepository;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryPdc;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryPdcRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class LoanPaymentInventoryAssembler {
	
	private final FromJsonHelper fromJsonHelper;
    private final PaymentInventoryRepository paymentInventoryRepository;
    private final PaymentInventoryPdcRepository paymentInventoryPdc;
    
    @Autowired
    public LoanPaymentInventoryAssembler(final FromJsonHelper fromJsonHelper, final PaymentInventoryRepository paymentInventoryRepository,
    		final PaymentInventoryPdcRepository paymentInventoryPdcRepository){
    	this.fromJsonHelper = fromJsonHelper;
    	this.paymentInventoryRepository = paymentInventoryRepository;
    	this.paymentInventoryPdc = paymentInventoryPdcRepository;
    }
    
    
    public Set<PaymentInventoryPdc> fromParsedJson(final JsonElement element){
    	
    	final Set<PaymentInventoryPdc> paymentInventory = new HashSet<>();
 
    	if(element.isJsonObject()){
    		final JsonObject topLevelJsonElement = element.getAsJsonObject();
    		final String dateFormat = this.fromJsonHelper.extractDateFormatParameter(topLevelJsonElement);
            final Locale locale = this.fromJsonHelper.extractLocaleParameter(topLevelJsonElement);
            
            if (topLevelJsonElement.has("pdcData") && topLevelJsonElement.get("pdcData").isJsonArray()) {
            		
            	final JsonArray array = topLevelJsonElement.get("pdcData").getAsJsonArray();
            	for (int i = 0; i < array.size(); i++) {
            		final JsonObject paymentInventorys = array.get(i).getAsJsonObject();
            		final Long id = this.fromJsonHelper.extractLongNamed("id", paymentInventorys);
            		
            		final Integer period = this.fromJsonHelper.extractIntegerNamed("period", paymentInventorys, locale);
            		
            		final LocalDate date = this.fromJsonHelper.extractLocalDateNamed("date", paymentInventorys, dateFormat, locale);
            		
            		final BigDecimal amount = this.fromJsonHelper.extractBigDecimalNamed("amount", paymentInventorys, locale);
            		
            		final LocalDate chequeDate = this.fromJsonHelper.extractLocalDateNamed("chequeDate", paymentInventorys, dateFormat, locale);
            		
            		final Long chequeno = this.fromJsonHelper.extractLongNamed("chequeNo", paymentInventorys);
            		
            		final String nameOfBank = this.fromJsonHelper.extractStringNamed("nameOfBank", paymentInventorys);
            		
            		final String ifscCode = this.fromJsonHelper.extractStringNamed("ifscCode", paymentInventorys);
            		
            		final Integer presentationStatus = this.fromJsonHelper.extractIntegerNamed("presentationStatus", paymentInventorys, locale);
            		
            		final boolean makePresentation = this.fromJsonHelper.extractBooleanNamed("makePresentation", paymentInventorys);
            				
            		/*PdcPresentationStatus options = null;
                    if (options != null) {
                       options = PdcPresentationStatus.fromInt(presentationStatus);
                    }*/
        
                    final PaymentInventoryPdc paymentInv = PaymentInventoryPdc.createNew(period, date, amount,
                            chequeDate, chequeno, nameOfBank, ifscCode, presentationStatus,makePresentation);
                    
                    paymentInventory.add(paymentInv);
            	}
            }
                
            
    	}
		return paymentInventory;
    }
    
    
}
