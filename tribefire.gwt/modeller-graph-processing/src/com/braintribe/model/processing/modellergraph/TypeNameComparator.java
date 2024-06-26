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
package com.braintribe.model.processing.modellergraph;

import java.util.Comparator;

import com.braintribe.model.meta.GmType;

public class TypeNameComparator implements Comparator<GmType> {
	@Override
	public int compare(GmType o1, GmType o2) {
		String t1 = o1.getTypeSignature();
		String t2 = o2.getTypeSignature();
		
		int packageDelimiter1 = t1.lastIndexOf('.');
		int packageDelimiter2 = t2.lastIndexOf('.');
		
		String package1 = t1.substring(0, packageDelimiter1);
		String package2 = t2.substring(0, packageDelimiter2);
		
		String simpleName1 = t1.substring(packageDelimiter1 + 1);
		String simpleName2 = t2.substring(packageDelimiter2 + 1);
		
		int res = simpleName1.compareToIgnoreCase(simpleName2);
		
		if (res != 0) 
			return res;
		else
			return package1.compareToIgnoreCase(package2);
	}
}
