package org.apache.fineract.portfolio.loanaccount.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.AbstractPersistable;


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
	
	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentinventory", orphanRemoval = true)
    private Set<PaymentInventoryPdc> paymentInventoryPdc = new HashSet<>();
    
	
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Set<PaymentInventoryPdc> paymentInventoryPdc) {
	        final Integer periods = loan.getLoanRepaymentScheduleDetail().getNumberOfRepayments();
	        final boolean isDirectDebitActive = command.booleanPrimitiveValueOfParameterNamed("isDirectDebitActive");
	        
	        return createNewFromJson(loan,command, periods, isDirectDebitActive, paymentInventoryPdc);
	    }
	 
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Integer periods, final boolean isDirectDebitActive, final Set<PaymentInventoryPdc> paymentInventoryPdc){
		 return new PaymentInventory(loan, periods, isDirectDebitActive, paymentInventoryPdc);
	 }
	
	public PaymentInventory(final Loan loan, final Integer periods, final boolean isDirectDebiActive, final Set<PaymentInventoryPdc> paymentInventoryPdc){
		this.loan = loan;
		this.periods = periods;
		this.isDirectDebitactive = isDirectDebiActive;
		this.paymentInventoryPdc = paymentInventoryPdc;
	}
}
