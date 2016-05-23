package org.apache.fineract.portfolio.loanaccount.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.useradministration.domain.Permission;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.access.method.P;


@Entity
@Table(name = "m_payment_inventory")
public class PaymentInventory extends AbstractPersistable<Long>{

	@ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", referencedColumnName = "id", nullable = false)
    private Loan loan;
	
	@Column(name = "periods", nullable = false)
	private Integer periods;
	
	@Column(name = "is_directDebitActive", nullable = false)
    private boolean isDirectDebitactive;

	@OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_inventory_id", nullable = false)
    private Set<PaymentInventoryPdc> paymentInventoryPdc;

	
	
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Set<PaymentInventoryPdc> paymentInventoryPdc) {
	        final Integer periods = loan.getLoanRepaymentScheduleDetail().getNumberOfRepayments();
	        final boolean isDirectDebitActive = command.booleanPrimitiveValueOfParameterNamed("isDirectDebitActive");
	        
	        return createNewFromJson(loan,command, periods, isDirectDebitActive, paymentInventoryPdc);
	    }
	 
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Integer periods, final boolean isDirectDebitActive, final Set<PaymentInventoryPdc> paymentInventoryPdc){
		 return new PaymentInventory(loan, periods, isDirectDebitActive, paymentInventoryPdc);
	 }
	
	public PaymentInventory(final Loan loan, final Integer periods, final boolean isDirectDebitActive, final Set<PaymentInventoryPdc> paymentInventoryPdc){
		this.loan = loan;
		this.periods = periods;
		this.isDirectDebitactive = isDirectDebitActive;
		this.paymentInventoryPdc = paymentInventoryPdc;
	}
	
	public Long getPaymentInventoryId(){
		return this.getId();
	}
	
}
