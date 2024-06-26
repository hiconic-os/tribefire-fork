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

import java.util.List;
import java.util.stream.Collectors;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ComparisonResult extends GenericEntity {
	EntityType<ComparisonResult> T = EntityTypes.T(ComparisonResult.class);

	String valuesDiffer = "valuesDiffer";
	String expectedValuesDiffer = "expectedValuesDiffer";
	String oldValue = "oldValue";
	String newValue = "newValue";
	String expectedDiffs = "expectedDiffs";
	String unexpectedDiffs = "unexpectedDiffs";

	void setValuesDiffer(boolean valuesDiffer);
	boolean getValuesDiffer();

	void setExpectedValuesDiffer(boolean expectedValuesDiffer);
	boolean getExpectedValuesDiffer();

	void setOldValue(Object oldValue);
	Object getOldValue();

	void setNewValue(Object newValue);
	Object getNewValue();

	void setExpectedDiffs(List<Diff> expectedDiffs);
	List<Diff> getExpectedDiffs();

	List<Diff> getUnexpectedDiffs();
	void setUnexpectedDiffs(List<Diff> unexpectedDiffs);

	static ComparisonResult create(boolean areEqual, Object first, Object second, List<Diff> expectedDiffs, List<Diff> unexpectedDiffs) {
		ComparisonResult result = ComparisonResult.T.create();
		result.setValuesDiffer(!areEqual);
		result.setExpectedValuesDiffer(expectedDiffs.stream().anyMatch(Diff::getValuesDiffer));
		result.setOldValue(first);
		result.setNewValue(second);
		result.setExpectedDiffs(expectedDiffs);
		result.setUnexpectedDiffs(unexpectedDiffs);
		return result;
	}

	default String expectedDiffsAsStringMessage() {
		return "Comparison result: objects are " + (getExpectedValuesDiffer() ? "not " : "") + "equal\n"
				+ getExpectedDiffs().stream().map(Diff::toStringRecord).collect(Collectors.joining("\n"));
	}

	default String unexpectedDiffsAsStringMessage() {
		return "Comparison result: objects are " + (getValuesDiffer() ? "not " : "") + "equal\n"
				+ getUnexpectedDiffs().stream().map(Diff::toStringRecord).collect(Collectors.joining("\n"));
	}
}
