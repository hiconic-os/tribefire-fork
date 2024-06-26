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
package com.braintribe.model.processing.xmi.converter.experts;

import com.braintribe.model.meta.GmMetaModel;

/**
 * helper class to build the artifact binding information
 * 
 * supports gwt module name appended via an $ to the model name
 * 
 * @author pit
 *
 */
public class DeclaringModelBinding {
	private String name;
	private GmMetaModel model;
	
	public DeclaringModelBinding( GmMetaModel model) {
		this.model = model;
		this.name = model.getName();		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public GmMetaModel getModel() {
		return model;
	}
	public void setModel(GmMetaModel model) {
		this.model = model;
	}

	public String toString() {
		return name;
	}
	
	public boolean matchesArtifact(String modelName) {		
		if (model.getName().equals( modelName))
			return true;
		return false;
	}
	
	
	public boolean matchesArtifact(GmMetaModel suspect) {
		if (model == suspect)
			return true;
		if (model.getName().equals( name))
			return true;
		return false;
	}

	public boolean matchesArtifact(DeclaringModelBinding artifact) {
		if (name.equals( artifact.getName()))
			return true;
		return false;
	}
	

}
