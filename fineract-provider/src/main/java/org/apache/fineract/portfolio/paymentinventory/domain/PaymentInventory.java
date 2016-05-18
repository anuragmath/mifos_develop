package org.apache.fineract.portfolio.paymentinventory.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
import org.apache.fineract.portfolio.paymentinventory.data.PaymentInventoryPdcData;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "m_payment_inventory")
public class PaymentInventory extends AbstractPersistable<Long>{
	
	@Column(name = "periods" , nullable = false)
	private Integer periods;
	
	
	@Column(name = "isDirectDebitActive", nullable = true)
	private Boolean isDirectDebitActive;
	
	@OrderBy(value = "date, id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loan", orphanRemoval = true)
    private List<PaymentInventoryPdc> paymentInventoryPdc = new ArrayList<>();
	
	
	public static PaymentInventory createInventory(final Integer periods, final boolean isDirectDebitActive, final List<PaymentInventoryPdcData> paymentInventoryPdc){
		return new PaymentInventory(periods, isDirectDebitActive,paymentInventoryPdc);
	}

	private PaymentInventory(final Integer periods, final boolean isDirectDebitActive, final List<PaymentInventoryPdcData> paymentInventoryPdc){
		this.periods = periods;
		this.isDirectDebitActive = isDirectDebitActive;
		this.paymentInventoryPdc = paymentInventoryPdc;
	}
	

}
