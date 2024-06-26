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
package tribefire.extension.messaging.model.comparison;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Diff extends GenericEntity {
	EntityType<Diff> T = EntityTypes.T(Diff.class);

	String propertyPath = "propertyPath";
	String oldValue = "oldValue";
	String newValue = "newValue";
	String valuesDiffer = "valuesDiffer";
	String descr = "descr";

	Object getNewValue();
	void setNewValue(Object newValue);

	Object getOldValue();
	void setOldValue(Object oldValue);

	boolean getValuesDiffer();
	void setValuesDiffer(boolean valuesDiffer);

	String getPropertyPath();
	void setPropertyPath(String propertyPath);

	String getDescription(); //Description!
	void setDescription(String description);

	static Diff createDiff(String propertyPath, Object first, Object second, boolean valuesDiffer, String description) {
		Diff diff = Diff.T.create();
		diff.setPropertyPath(propertyPath);
		diff.setOldValue(first);
		diff.setNewValue(second);
		diff.setValuesDiffer(valuesDiffer);
		diff.setDescription(description);
		return diff;
	}

	default String toStringRecord() {
		return "property path: " + getPropertyPath() + (getValuesDiffer() ? ", VALUE CHANGED, " : ", ") + getDescription() + ", was: " + getOldValue()
				+ ", now: " + getNewValue();
	}
}
