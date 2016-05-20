package org.apache.fineract.portfolio.loanaccount.service;

import java.util.Set;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
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

	
