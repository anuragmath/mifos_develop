package org.apache.fineract.portfolio.loanaccount.loanschedule.service;

import java.math.BigDecimal;

public class RateFunction {
	
	public final static BigDecimal rate(int paymentsPerYear,BigDecimal paymentAmount,Double presentValue, Double futureValue, Double dueEndOrBeginning,Double interest){
	    //If interest, futureValue, dueEndorBeginning was not set, set now
					paymentAmount= paymentAmount.negate();
					double pay = paymentAmount.doubleValue();
					
	                if(interest == null)
	                interest = 0.01;
	                
	                if (futureValue == null)
	                futureValue = 0.0;
	                
	                if (dueEndOrBeginning == null)
	                dueEndOrBeginning = 0.0;

	                float FINANCIAL_MAX_ITERATIONS = 128;//Bet accuracy with 128
	                double FINANCIAL_PRECISION = 0.0000001;//1.0e-8

	                double y;
					double y0, y1, x0, x1 = 0, f = 0, i = 0;
	                Double rate = interest;
	                if (Math.abs(rate) < FINANCIAL_PRECISION){
	                     y = presentValue * (1 + paymentsPerYear * rate) + pay * (1 + rate * dueEndOrBeginning) * paymentsPerYear + futureValue;
	                    }else{
	                        f = Math.exp(paymentsPerYear * Math.log(1 + rate));
	                        y = presentValue * f + pay * (1 / rate + dueEndOrBeginning) * (f - 1) + futureValue;
	                    }
	                y0 = presentValue + pay * paymentsPerYear + futureValue;
	                y1 = presentValue * f + pay * (1 / rate + dueEndOrBeginning) * (f - 1) + futureValue;

	        // find root by Newton secant method
	                i = x0 = 0.0;
	                x1 = rate;
	                while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION)
	                        && (i < FINANCIAL_MAX_ITERATIONS)){
	                rate = (y1 * x0 - y0 * x1) / (y1 - y0);
	                x0 = x1;
	                x1 = rate;

	                if (Math.abs(rate) < FINANCIAL_PRECISION){
	                y = presentValue * (1 + paymentsPerYear * rate) + pay * (1 + rate * dueEndOrBeginning) * paymentsPerYear + futureValue;
	                }else{
	                f = Math.exp(paymentsPerYear * Math.log(1 + rate));
	                y = presentValue * f + pay * (1 / rate + dueEndOrBeginning) * (f - 1) + futureValue;
	            }

	                y0 = y1;
	                y1 = y;
	                ++i;
	        }
	                
	        return BigDecimal.valueOf(rate);
	    }
}
