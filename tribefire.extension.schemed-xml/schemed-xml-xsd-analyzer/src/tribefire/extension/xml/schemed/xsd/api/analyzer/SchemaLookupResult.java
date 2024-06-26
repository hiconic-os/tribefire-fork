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
package tribefire.extension.xml.schemed.xsd.api.analyzer;

import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.SchemaEntity;

public class SchemaLookupResult <T extends SchemaEntity>{
	private T found;
	private Namespace targetNamespace;
	
	public SchemaLookupResult(Namespace targetNamespace, T found) {
		this.targetNamespace = targetNamespace;
		this.found = found;		
	}

	public T getFound() {
		return found;
	}

	public void setFound(T found) {
		this.found = found;
	}

	public Namespace getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(Namespace targetNamespace) {
		this.targetNamespace = targetNamespace;
	}
	
	
	

}
