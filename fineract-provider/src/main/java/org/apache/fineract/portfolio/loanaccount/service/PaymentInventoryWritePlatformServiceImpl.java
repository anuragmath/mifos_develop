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

import java.util.Set;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventory;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryPdc;
import org.apache.fineract.portfolio.loanaccount.domain.PaymentInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;

@Service
public class PaymentInventoryWritePlatformServiceImpl implements PaymentInventoryWritePlatformService {
	
	final PaymentInventoryRepository paymentInventory;
	final LoanAssembler loanAssembler;
	final FromJsonHelper fromJsonHelper;
	final PaymentInventoryRepository paymentInventoryRepository;
	final LoanPaymentInventoryAssembler loanPaymentInventory;
	
	@Autowired
	public  PaymentInventoryWritePlatformServiceImpl(final PaymentInventoryRepository paymentInventory,
			final LoanAssembler loanAssembler, final PaymentInventoryRepository paymentInventoryRepository,
			final FromJsonHelper fromJsonHelper, final LoanPaymentInventoryAssembler loanPaymentInventory){
		this.paymentInventory = paymentInventory;
		this.loanAssembler = loanAssembler;
		this.fromJsonHelper = fromJsonHelper;
		this.paymentInventoryRepository = paymentInventoryRepository;
		this.loanPaymentInventory = loanPaymentInventory;
	}
	
	@Transactional
	@Override
	public CommandProcessingResult addPaymentInventory(final Long loanId, final JsonCommand command){
		

		final Loan loan = this.loanAssembler.assembleFrom(loanId);	
		
		final JsonElement element = command.parsedJson();
	
		final Set<PaymentInventoryPdc> paymentInventoryPdc = this.loanPaymentInventory.fromParsedJson(element);
		
		final PaymentInventory paymentInventory = PaymentInventory.createNewFromJson(loan, command, paymentInventoryPdc);
		
		this.paymentInventoryRepository.save(paymentInventory);
		
		
		return new CommandProcessingResultBuilder() //
	                .withCommandId(command.commandId()) //
	                .withEntityId(paymentInventory.getId()) //
	                .withLoanId(loanId) //
	                .build();
	}
}
