package org.apache.fineract.portfolio.loanaccount.loanschedule.service;

import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.service.LoanAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IRRCalculate {
	
	private final LoanAssembler loanAssembler;
	
	@Autowired
	public IRRCalculate(final LoanAssembler loanAssembler){
		this.loanAssembler = loanAssembler;
	}

	public double[] IRRCal(final Long loanId){
		
		final Loan loan = this.loanAssembler.assembleFrom(loanId);
		
		LoanRepaymentScheduleInstallment repayment;
        final MonetaryCurrency currency = loan.getCurrency();
		Money principal;
		Money interest;
		final double[] values = new double[loan.getTermFrequency()]; 
        for(int i = 1;i<loan.getTermFrequency();i++){
        	repayment = loan.fetchRepaymentScheduleInstallment(i);
        	if(i==1){
        		principal = repayment.getPrincipalOutstanding(currency);  		
        		values[i-1]=(-(principal.getAmount().doubleValue()));
        	}
        	else{
        		principal = repayment.getPrincipalOutstanding(currency);
        		interest = repayment.getInterestCharged(currency);
        		values[i-1]=((principal.getAmount().doubleValue() + interest.getAmount().doubleValue()));
        	}
        }
		return values;
	}

}
