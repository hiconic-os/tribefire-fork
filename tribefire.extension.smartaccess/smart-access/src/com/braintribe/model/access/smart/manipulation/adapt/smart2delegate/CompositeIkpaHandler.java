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
import com.braintribe.model.accessdeployment.smart.meta.CompositeInverseKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.CompositeKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.InverseKeyPropertyAssignment;
import com.braintribe.model.generic.manipulation.AddManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.ClearCollectionManipulation;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.manipulation.RemoveManipulation;

/**
 * Covers properties with {@link CompositeKeyPropertyAssignment} mapping.
 */
public class CompositeIkpaHandler implements Smart2DelegateHandler<CompositeInverseKeyPropertyAssignment> {

	private final IkpaHandler ikpaHandler;

	private Set<InverseKeyPropertyAssignment> ikpas;

	public CompositeIkpaHandler(IkpaHandler ikpaHandler) {
		this.ikpaHandler = ikpaHandler;
	}

	/* This is used as local variable inside methods, but is declared here to make code nicer */
	protected Manipulation delegateManipulation;

	@Override
	public void loadAssignment(CompositeInverseKeyPropertyAssignment assignment) {
		this.ikpas = assignment.getInverseKeyPropertyAssignments();
	}

	@Override
	public void convertToDelegate(ChangeValueManipulation manipulation) throws ModelAccessException {
		for (InverseKeyPropertyAssignment kpa: ikpas) {
			ikpaHandler.loadAssignment(kpa);
			ikpaHandler.convertToDelegate(manipulation);
		}
	}

	@Override
	public void convertToDelegate(AddManipulation manipulation) throws ModelAccessException {
		for (InverseKeyPropertyAssignment kpa: ikpas) {
			ikpaHandler.loadAssignment(kpa);
			ikpaHandler.convertToDelegate(manipulation);
		}
	}

	@Override
	public void convertToDelegate(RemoveManipulation manipulation) throws ModelAccessException {
		for (InverseKeyPropertyAssignment kpa: ikpas) {
			ikpaHandler.loadAssignment(kpa);
			ikpaHandler.convertToDelegate(manipulation);
		}
	}

	@Override
	public void convertToDelegate(ClearCollectionManipulation manipulation) throws ModelAccessException {
		for (InverseKeyPropertyAssignment kpa: ikpas) {
			ikpaHandler.loadAssignment(kpa);
			ikpaHandler.convertToDelegate(manipulation);
		}
	}

}
