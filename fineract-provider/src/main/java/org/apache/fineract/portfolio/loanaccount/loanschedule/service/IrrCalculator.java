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


public class IrrCalculator {


	    public static double irr(double[] values, double guess) {
	        int maxIterationCount = 100;
	        double absoluteAccuracy = 1E-7;

	        double x0 = guess;
	        double x1;

	        int i = 0;
	        while (i < maxIterationCount) {

	            // the value of the function (NPV) and its derivate can be calculated in the same loop
	            double fValue = 0;
	            double fDerivative = 0;
	            for (int k = 0; k < values.length; k++) {
	                fValue += values[k] / Math.pow(1.0 + x0, k) ;
	                System.out.println(values[k]);
	                fDerivative += -k * values[k] / Math.pow(1.0 + x0, k + 1);
	            }

	            // the essense of the Newton-Raphson Method
	            x1 = x0 - fValue/fDerivative;

	            if (Math.abs(x1 - x0) <= absoluteAccuracy) {
	                return x1;
	            }

	            x0 = x1;
	            ++i;
	        }
	        // maximum number of iterations is exceeded
	        return Double.NaN;
	    }
		
	}
		

