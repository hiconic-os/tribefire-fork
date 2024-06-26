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
package tribefire.platform.wire.space.cortex.accesses;

import java.util.function.Predicate;

import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.generic.manipulation.AtomicManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.manipulation.ManipulationType;
import com.braintribe.model.generic.manipulation.Owner;
import com.braintribe.model.generic.reflection.Property;

/**
 * A {@link Manipulation} Filter that passes all manipulations except {@link ChangeValueManipulation} for the
 * {@link Deployable#deploymentStatus} property.
 * 
 * @author gunther
 */
public class DeploymentStateManipulationFilter implements Predicate<AtomicManipulation> {

	public static final DeploymentStateManipulationFilter INSTANCE = new DeploymentStateManipulationFilter();

	private static final Property deploymentStateProperty = Deployable.T.getProperty(Deployable.deploymentStatus);

	@Override
	public boolean test(AtomicManipulation m) {
		if (m.manipulationType() == ManipulationType.CHANGE_VALUE) {
			ChangeValueManipulation cvm = (ChangeValueManipulation) m;
			Owner owner = cvm.getOwner();

			return deploymentStateProperty != owner.property();
		}
		return true;

	}
}
