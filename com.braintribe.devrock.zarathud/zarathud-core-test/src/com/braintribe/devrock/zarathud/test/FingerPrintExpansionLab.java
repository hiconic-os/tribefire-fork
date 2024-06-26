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

import org.junit.Test;

import com.braintribe.devrock.zed.forensics.fingerprint.FingerPrintExpert;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public class FingerPrintExpansionLab extends AbstractFingerprintLab {
	
	@Test
	public void test() {
		String v1 = "group:com.braintribe.gm/artifact:gm-core-api/package:com.braintribe.gm/type:GenericEntity";
		String v2 = "property:myProperty";
		String v3 = v1 + "/" + v2;
		
		FingerPrint fp1 = FingerPrintExpert.build(v1);
		FingerPrint fp3 = FingerPrintExpert.build( v3);
		
		fp1 = FingerPrintExpert.attach(fp1, v2);
		
		testMatching(fp1, fp3, true);		
	}
	
}
