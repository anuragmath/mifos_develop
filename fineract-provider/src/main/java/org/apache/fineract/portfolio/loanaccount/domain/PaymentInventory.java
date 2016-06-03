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

import org.apache.fineract.infrastructure.core.api.JsonCommand;
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
