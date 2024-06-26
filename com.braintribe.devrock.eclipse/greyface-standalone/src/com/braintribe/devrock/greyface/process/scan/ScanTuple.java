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
package com.braintribe.devrock.greyface.process.scan;

import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Solution;

public class ScanTuple {
	public Dependency dependency;
	public int level;
	public int index;
	public Solution importParent;
	
	
	public ScanTuple(Dependency dependency, int level, int index, Solution importParent) {
		this.dependency = dependency;
		this.level = level;
		this.index = index;
		this.importParent = importParent;
	}
}
