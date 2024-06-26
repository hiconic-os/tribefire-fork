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

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ShallowSubstitutingModel;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.Substitution;

/**
 * a context to specify the substitution of types, grouped by the model of origin 
 * @author pit
 *
 * @param <T>
 */
public class ShallowSubstitutionModelContext<T extends ShallowSubstitutionModelConsumer> implements SubstitutionConsumer {
	
	private ShallowSubstitutingModel substitionModel = ShallowSubstitutingModel.T.create();
	private T consumer;
	
	public ShallowSubstitutionModelContext(T consumer) {
		this.consumer = consumer;
	}
	
	public ShallowSubstitutionModelContext<T> modelName( String name) {			
		substitionModel.setDeclaringModel( name);
		return this;
	}
	
	/**
	 * declared the types and their substitutions
	 * @return - a {@link SubstitutionContext}
	 */
	public SubstitutionContext<ShallowSubstitutionModelContext<T>> substitution() {
		return new SubstitutionContext<ShallowSubstitutionModelContext<T>>( this);
	}		
	
	@Override
	public void accept(Substitution substitution) {	
		substitionModel.getSubstitutions().add(substitution);
	}
	

	/**
	 * finish and return to parent context
	 * @return - the parent context
	 */
	public T close() {
		consumer.accept( substitionModel);
		return consumer;
	}
}
