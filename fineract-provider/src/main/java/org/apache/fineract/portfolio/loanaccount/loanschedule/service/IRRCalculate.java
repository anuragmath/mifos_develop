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

package org.apache.fineract.portfolio.loanaccount.loanschedule.service;

import java.math.BigDecimal;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.service.LoanAssembler;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IRRCalculate {
	
	private final LoanAssembler loanAssembler;
	
	@Autowired
	public IRRCalculate(final LoanAssembler loanAssembler){
		this.loanAssembler = loanAssembler;
	}
	
	/*
	 * We need to get the principal based on the states, 
	 * When a repayment is recorded it should have the outstanding principal
	 */

	@SuppressWarnings("null")
	public double[] IRRCal(final Long loanId){
		
		final Loan loan = this.loanAssembler.assembleFrom(loanId);
		
		LoanRepaymentScheduleInstallment repayment;
        final MonetaryCurrency currency = loan.getCurrency();
		BigDecimal principal;
		BigDecimal emi;
		int term = loan.getTermFrequency();
		LocalDate disbursementDate = loan.getDisbursementDate();
		LocalDate currentDate = new LocalDate();
		int repaidterm = Months.monthsBetween(disbursementDate, currentDate).getMonths();
		final double[] values = new double[term-repaidterm-loan.getAdvanceEmiN()+1];
		if(loan.getAdvanceEmiN()==0){
			term = loan.getTermFrequency()+1;
			if (loan.isSubmittedAndPendingApproval()){
    				principal = loan.getProposedPrincipal();  	
    			}
    			else if (!loan.isDisbursed()){
    				principal = loan.getApprovedPrincipal();	
    			}
    			else
    				principal = loan.getDisbursedAmount();
			values[0]=(-(principal.doubleValue()));
		}
		else{
			repayment = loan.fetchRepaymentScheduleInstallment(1);
			emi = repayment.getTotalPrincipalAndInterest(currency).getAmount();
			principal = loan.getProposedPrincipal().subtract(emi.multiply(new BigDecimal(loan.getAdvanceEmiN()))); 
			values[0]=Math.round((-(principal.doubleValue())));
		}
		term = loan.getTermFrequency() - repaidterm - loan.getAdvanceEmiN()+1;
        for(int i = 1 ; i < term ; i++){
        		repayment = loan.fetchRepaymentScheduleInstallment(i);
        		emi = repayment.getTotalPrincipalAndInterest(currency).getAmount();
        		values[i]= Math.round((emi.doubleValue()));
        	}
		return values;
	}

}
