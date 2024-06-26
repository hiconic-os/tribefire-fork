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
package com.braintribe.model.processing.xmi.converter.tagdefinition;

import com.braintribe.model.processing.xmi.converter.experts.DeclaringModelBinding;

public class TagDefinition {

	private String xmiId;
	private String name;

	private DeclaringModelBinding artifactBinding;

	public String getXmiId() {
		return xmiId;
	}

	public void setXmiId(String xmiId) {
		this.xmiId = xmiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DeclaringModelBinding getArtifactBinding() {
		return artifactBinding;
	}

	public void setArtifactBinding(DeclaringModelBinding artifactBinding) {
		this.artifactBinding = artifactBinding;
	}

}
