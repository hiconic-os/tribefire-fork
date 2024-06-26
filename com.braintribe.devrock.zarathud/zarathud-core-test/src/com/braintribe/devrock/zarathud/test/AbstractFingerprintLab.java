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
package com.braintribe.devrock.zarathud.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import com.braintribe.devrock.zed.forensics.fingerprint.FingerPrintExpert;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public abstract class AbstractFingerprintLab {
	/**
	 * @param v1 - first string representation
	 * @param v2 - second string representation 
	 * @return - true if they match
	 */
	protected boolean testOnEquivalency( String v1, String v2) {
		List<String> tokens1 = Arrays.asList(v1.split( "/"));
		List<String> tokens2 = Arrays.asList(v2.split( "/"));
		
		for (String token : tokens1) {
			// value exists like that -> ok
			if (tokens2.contains( token))
				continue;
			// value doesn't exist, but key is a wild card -> ok
			if (token.endsWith( ":*"))
				continue;
			// neither -> fail
			Assert.fail("expression [" + v2 + "] is not compatible with expected [" + v1 + "]");
			return false;
		}
		return true;
	}
	
	protected void testMatching( String v1, String v2, boolean matchExpected) {
		FingerPrint lock = FingerPrintExpert.build(v1);
		FingerPrint key = FingerPrintExpert.build( v2);			
		testMatching( lock, key, matchExpected);
	}
	
	protected void testMatching( FingerPrint lock, FingerPrint key, boolean matchExpected) {
		boolean matches = FingerPrintExpert.matches(lock, key);
		if (matchExpected) {
			Assert.assertTrue( "key [" + key.toString() + "] doesn't match lock [" + lock.toString() + "]", matches);				
		}
		else {
			Assert.assertTrue( "key [" + key.toString() + "] matches lock [" + lock.toString() + "]", !matches);
		}		
	}

}
