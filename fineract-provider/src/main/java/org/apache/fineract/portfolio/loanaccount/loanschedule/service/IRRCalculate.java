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
    			else {
    				principal = loan.getApprovedPrincipal();	
    			}
			values[0]=(-(principal.doubleValue()));
		}
		else{
			repayment = loan.fetchRepaymentScheduleInstallment(1);
			emi = repayment.getTotalPrincipalAndInterest(currency).getAmount();
			principal = loan.getProposedPrincipal().subtract(emi.multiply(new BigDecimal(loan.getAdvanceEmiN()))); 
			values[0]=(-(principal.doubleValue()));
		}
		term = loan.getTermFrequency() - repaidterm - loan.getAdvanceEmiN()+1;
        for(int i = 1 ; i < term ; i++){
        		repayment = loan.fetchRepaymentScheduleInstallment(i);
        		emi = repayment.getTotalPrincipalAndInterest(currency).getAmount();
        		values[i]= (emi.doubleValue());
        	}
		return values;
	}

}
