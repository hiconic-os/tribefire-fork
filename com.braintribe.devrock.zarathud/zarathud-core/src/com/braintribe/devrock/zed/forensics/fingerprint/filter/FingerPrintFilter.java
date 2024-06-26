// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.devrock.zed.forensics.fingerprint.filter;

import java.util.function.Predicate;

import com.braintribe.devrock.zed.forensics.fingerprint.FingerPrintExpert;
import com.braintribe.zarathud.model.forensics.FingerPrint;

/**
 * filter to use the {@link FingerPrintExpert}'s matching feature within streams of *LOCK*.
 * Passed {@link FingerPrint} in constructor is used as *KEY*, i.e. must be either as qualified 
 * or less qualified than the *LOCK* in order to return *LOCK* 
 * 
 * @author pit
 *
 */
public class FingerPrintFilter implements Predicate<FingerPrint> {
	private FingerPrint key;	

	/**
	 * @param key - a {@link FingerPrint} instance to use as KEY
	 */
	public FingerPrintFilter( FingerPrint key) {
		this.key = key;
	}
	
	public FingerPrintFilter( FingerPrint key, String ... slotsToIgnore) {
		this.key = key;
	}
	
	
	/**
	 * @param expression - a {@link FingerPrint} in string notation to use as KEY
	 */
	public FingerPrintFilter( String expression) {
		this.key = FingerPrintExpert.build(expression);
	}
	
	@Override
	public boolean test(FingerPrint lock) {
		return FingerPrintExpert.matches(lock, key);
	}

	
}
