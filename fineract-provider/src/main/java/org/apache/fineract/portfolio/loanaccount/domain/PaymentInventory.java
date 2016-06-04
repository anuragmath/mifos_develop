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

package org.apache.fineract.portfolio.loanaccount.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
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

	@OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "payment_inventory_id", nullable = false)
    private Set<PaymentInventoryPdc> paymentInventoryPdc;
	
	@Column(name = "pdc_type", nullable = false)
	private Integer pdcType;
	
	@Column(name = "is_seriesCheques", nullable = false)
	private boolean isSeriesCheques;
	
	@Column(name = "is_chequesDisbursed", nullable = false)
	private boolean isChequesDisbursed;
	
	
	public PaymentInventory(){
		this.loan = null;
		this.periods = null;
		this.isDirectDebitactive = false;
		this.paymentInventoryPdc = null;
		this.isChequesDisbursed = false;
		this.isSeriesCheques = false;
		this.pdcType = null;
	}
	
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Set<PaymentInventoryPdc> paymentInventoryPdc) {
	        final Integer periods = loan.getLoanRepaymentScheduleDetail().getNumberOfRepayments();
	        final boolean isDirectDebitActive = command.booleanPrimitiveValueOfParameterNamed("isDirectDebitActive");
	        final Integer pdcType = command.integerValueOfParameterNamed("pdcType");
	        final boolean isSeriesCheques = command.booleanPrimitiveValueOfParameterNamed("isSeriesCheques");
	        final boolean isChequesDisbursed = command.booleanPrimitiveValueOfParameterNamed("isChequesDisbursed");
	        return createNewFromJson(loan,command, periods, isDirectDebitActive, paymentInventoryPdc, pdcType, isSeriesCheques, isChequesDisbursed);
	    }
	 
	 public static PaymentInventory createNewFromJson(final Loan loan, final JsonCommand command, final Integer periods, final boolean isDirectDebitActive, final Set<PaymentInventoryPdc> paymentInventoryPdc,
			 final Integer pdcTypeValue, final boolean isSeriesCheques, final boolean isChequesDisbursed){
		 
		 final PdcTypeEnumOption pdcType = PdcTypeEnumOption.fromInt(pdcTypeValue);
		 return new PaymentInventory(loan, periods, isDirectDebitActive, paymentInventoryPdc, pdcType, isSeriesCheques, isChequesDisbursed);
	 }
	 
	
	public PaymentInventory(final Loan loan, final Integer periods, final boolean isDirectDebitActive, final Set<PaymentInventoryPdc> paymentInventoryPdc, final PdcTypeEnumOption pdcType,
			final boolean isSeriesCheques, final boolean isChequesDisbursed){
		this.loan = loan;
		this.periods = periods;
		this.isDirectDebitactive = isDirectDebitActive;
		this.paymentInventoryPdc = paymentInventoryPdc;
		this.isChequesDisbursed = isChequesDisbursed;
		this.isSeriesCheques = isSeriesCheques;
		this.pdcType = pdcType.getValue();
	}
	

	public Long getPaymentInventoryId(){
		return this.getId();
	}
	
	public boolean isDirectDebitActive(){
		return this.isDirectDebitactive;
	}
	
	public void setPdcType(final Integer pdcTypeValue){
		this.pdcType = pdcTypeValue;
	}
	
	public Integer getPdcType(){
		return this.pdcType;
	}
	
	public boolean isSeriesCheques(){
		return this.isSeriesCheques;
	}
	
	public boolean isChequesDisbursed(){
		return this.isChequesDisbursed;
	}
	
	public Set<PaymentInventoryPdc> getPaymentInventoryPdc(){
		 return this.paymentInventoryPdc;
	}
}
