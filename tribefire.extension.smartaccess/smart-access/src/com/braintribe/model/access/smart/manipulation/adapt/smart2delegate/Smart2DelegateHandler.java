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

import com.braintribe.model.access.ModelAccessException;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAssignment;
import com.braintribe.model.generic.manipulation.AddManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.ClearCollectionManipulation;
import com.braintribe.model.generic.manipulation.RemoveManipulation;

/**
 * 
 * @author peter.gazdik
 */
public interface Smart2DelegateHandler<T extends PropertyAssignment> {

	void loadAssignment(T assignment);

	void convertToDelegate(ChangeValueManipulation smartManipulation) throws ModelAccessException;

	void convertToDelegate(AddManipulation smartManipulation) throws ModelAccessException;

	void convertToDelegate(RemoveManipulation smartManipulation) throws ModelAccessException;

	void convertToDelegate(ClearCollectionManipulation smartManipulation) throws ModelAccessException;

}
