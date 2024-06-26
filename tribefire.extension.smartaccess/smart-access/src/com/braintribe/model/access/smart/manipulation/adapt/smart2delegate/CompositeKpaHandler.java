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
package com.braintribe.model.access.smart.manipulation.adapt.smart2delegate;

import java.util.Set;

import com.braintribe.model.access.ModelAccessException;
import com.braintribe.model.access.smart.manipulation.SmartManipulationContextVariables;
import com.braintribe.model.access.smart.manipulation.SmartManipulationProcessor;
import com.braintribe.model.accessdeployment.smart.meta.CompositeKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.KeyPropertyAssignment;
import com.braintribe.model.generic.manipulation.AddManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.ClearCollectionManipulation;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.manipulation.RemoveManipulation;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityPropertyMapping;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityPropertyMapping.CompositeKpaPropertyWrapper;

/**
 * Covers properties with {@link CompositeKeyPropertyAssignment} mapping.
 */
public class CompositeKpaHandler implements Smart2DelegateHandler<CompositeKeyPropertyAssignment> {

	private final SmartManipulationProcessor smp;
	private final SmartManipulationContextVariables $;

	private final StandardHandler standardPmh;

	private Set<KeyPropertyAssignment> kpas;
	private CompositeKpaPropertyWrapper compositeEpm;

	public CompositeKpaHandler(SmartManipulationProcessor smp, StandardHandler standardPmh) {
		this.smp = smp;
		this.$ = smp.context();
		this.standardPmh = standardPmh;
	}

	/* This is used as local variable inside methods, but is declared here to make code nicer */
	protected Manipulation delegateManipulation;

	@Override
	public void loadAssignment(CompositeKeyPropertyAssignment assignment) {
		compositeEpm = (CompositeKpaPropertyWrapper) smp.modelExpert().resolveEntityPropertyMapping($.currentSmartType, $.currentAccess,
				$.currentSmartOwner.getPropertyName());

		this.kpas = assignment.getKeyPropertyAssignments();
	}

	@Override
	public void convertToDelegate(ChangeValueManipulation manipulation) throws ModelAccessException {
		for (KeyPropertyAssignment kpa: kpas) {
			EntityPropertyMapping epm = compositeEpm.getPartialEntityPropertyMapping(kpa);

			standardPmh.loadAssignment(kpa, epm);
			standardPmh.convertToDelegate(manipulation);
		}
	}

	@Override
	public void convertToDelegate(AddManipulation manipulation) throws ModelAccessException {
		throw new UnsupportedOperationException("Method 'CompositeKpaHandler.convertToDelegate' is not implemented supported!");
	}

	@Override
	public void convertToDelegate(RemoveManipulation manipulation) throws ModelAccessException {
		throw new UnsupportedOperationException("Method 'CompositeKpaHandler.convertToDelegate' is not implemented supported!");
	}

	@Override
	public void convertToDelegate(ClearCollectionManipulation manipulation) throws ModelAccessException {
		throw new UnsupportedOperationException("Method 'CompositeKpaHandler.convertToDelegate' is not implemented supported!");
	}

}
