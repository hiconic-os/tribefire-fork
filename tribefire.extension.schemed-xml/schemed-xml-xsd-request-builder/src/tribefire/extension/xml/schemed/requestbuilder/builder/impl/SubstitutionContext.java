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
package tribefire.extension.xml.schemed.requestbuilder.builder.impl;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.itw.analysis.JavaTypeAnalysis;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.Substitution;

/**
 * a context to specify substitution.. 
 * @author pit
 *
 * @param <T>
 */
public class SubstitutionContext<T extends SubstitutionConsumer> implements SchemaAddressConsumer {
	private Substitution substitution = Substitution.T.create();
	private T consumer;
	
	public SubstitutionContext(T consumer) {
		this.consumer = consumer;
	}
	
	/**
	 * declare the matching address
	 * @return
	 */
	public SchemaAddressContext<SubstitutionContext<T>> schemaAddress() {
		return new SchemaAddressContext<SubstitutionContext<T>>(this);
	}

	@Override
	public void accept(SchemaAddress address) {
		substitution.setSchemaAddress(address);
	}
	
	/**
	 * declares the signature of the {@link GmEntityType} you want to use
	 * @param signature - the fully qualified type signature 
	 * @return - 
	 */
	public SubstitutionContext<T> replacementSignature(String signature) {
		substitution.setReplacementSignature(signature);
		return this;
	}
	
	/**
	 * if not set, the global id is derived from the signature (as JTA does) 
	 * @param globalId - the global id 
	 * @return
	 */
	public SubstitutionContext<T> replacementGlobalId(String globalId) {
		substitution.setReplacementGlobalId(globalId);
		return this;
	}
	
	/**
	 * finish and return to parent context
	 * @return - the parent context
	 */
	public T close() {
		// if not global id is set, derive it from the signature
		if (substitution.getReplacementGlobalId() == null) {
			substitution.setReplacementGlobalId( JavaTypeAnalysis.typeGlobalId( substitution.getReplacementSignature()));
		}
		consumer.accept( substitution);
		return consumer;
	}
	

}
