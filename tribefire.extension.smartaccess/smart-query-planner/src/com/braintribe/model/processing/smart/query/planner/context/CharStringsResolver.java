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
package com.braintribe.model.processing.smart.query.planner.context;

import static com.braintribe.utils.lcd.CollectionTools2.first;
import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.CombinationGroup;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.DelegateJoinGroup;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.SingleAccessCombinationGroup;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.SingleSourceGroup;

/**
 * 
 * @author peter.gazdik
 */
public class CharStringsResolver {

	private final Map<SourceNodeGroup, List<String>> charStringsForGroup = newMap();

	public List<String> getCharStrings(SourceNodeGroup group) {
		List<String> result = charStringsForGroup.get(group);

		if (result == null) {
			result = computeCharStringsFor(group);
			charStringsForGroup.put(group, result);
		}

		return result;
	}

	private List<String> computeCharStringsFor(SourceNodeGroup group) {
		List<String> result = newList();
		add(group, result);

		Collections.sort(result);

		return result;
	}

	private static void add(SourceNodeGroup group, List<String> result) {
		switch (group.nodeGroupType()) {
			case combination:
				add((CombinationGroup) group, result);
				return;
			case delegateQueryJoin:
				add((DelegateJoinGroup) group, result);
				return;
			case singleAccessCombination:
				add((SingleAccessCombinationGroup) group, result);
				return;
			case singleSource:
				add((SingleSourceGroup) group, result);
				return;
		}

		throw new SmartQueryPlannerException("Unknown group type '" + group.nodeGroupType() + "'.");
	}

	private static void add(SingleAccessCombinationGroup group, List<String> result) {
		for (SingleSourceGroup operand: group.operands)
			add(operand, result);
	}

	private static void add(SingleSourceGroup group, List<String> result) {
		result.add(stringFor(group.access, first(group.allNodes)));
	}

	private static void add(DelegateJoinGroup group, List<String> result) {
		add(group.materializedGroup, result);
		add(group.queryGroup, result);
	}

	private static void add(CombinationGroup group, List<String> result) {
		for (SourceNodeGroup operand: group.operands)
			add(operand, result);
	}

	private static String stringFor(IncrementalAccess access, EntitySourceNode sn) {
		return access.getExternalId() + "#" + sn.getDelegateGmType().getTypeSignature();
	}

}
