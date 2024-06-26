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
import com.braintribe.zarathud.model.data.FieldEntity;
import com.braintribe.zarathud.model.data.MethodEntity;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.data.natures.HasFieldsNature;
import com.braintribe.zarathud.model.data.natures.HasGenericNature;
import com.braintribe.zarathud.model.data.natures.HasMethodsNature;
import com.braintribe.zarathud.model.forensics.FingerPrint;

public class FingerPrintSourceFilter implements Predicate<ZedEntity> {
	
	private FingerPrint fingerPrint;

	public FingerPrintSourceFilter(FingerPrint fp) {
		this.fingerPrint = fp;				
	}

	@Override
	public boolean test(ZedEntity t) {
		// compare all fields other than ISSUE..
		// type 
		FingerPrint tfp = FingerPrintExpert.build( t);
		if (FingerPrintExpert.matches( tfp, fingerPrint))
			return true;
		
		// methods
		if (t instanceof HasMethodsNature) {			
			for (MethodEntity me : ((HasMethodsNature) t).getMethods()) {
				FingerPrint mfp = FingerPrintExpert.build(me);
				if (FingerPrintExpert.matches( mfp, fingerPrint)) {
					return true;
				}
				// return type, arguments, exceptions (not supported yet)
			} 			
		}
		// fields
		if (t instanceof HasFieldsNature) {
			for (FieldEntity fe : ((HasFieldsNature) t).getFields()) {
				FingerPrint ffp = FingerPrintExpert.build( fe);
				if (FingerPrintExpert.matches(ffp, fingerPrint)) {
					return true;
				}
			}
		}
		// generic entity stuff
		if (t instanceof HasGenericNature) {
			// properties are current *not* part of the extract..?? 
		}
		
		
		
		return false;
	}

}
