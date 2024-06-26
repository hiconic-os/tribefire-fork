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
package com.braintribe.model.processing.smart.query.planner.structure.adapter;

import java.util.Arrays;
import java.util.List;

import com.braintribe.model.accessdeployment.smart.meta.LinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.OrderedLinkPropertyAssignment;

/**
 * 
 */
public class LinkPropertyAssignmentWrapper implements DqjDescriptor {

	private final LinkPropertyAssignment assignment;
	private final boolean isLinkEntity;

	public LinkPropertyAssignmentWrapper(LinkPropertyAssignment assignment, Level level) {
		this.assignment = assignment;
		this.isLinkEntity = level == Level.linkEntity;
	}

	@Override
	public List<String> getJoinedEntityDelegatePropertyNames() {
		return Arrays.asList(getJoinedEntityDelegatePropertyName());
	}

	@Override
	public String getRelationOwnerDelegatePropertyName(String joinedEntityDelegatePropertyName) {
		return getRelationOwnerDelegatePropertyName();
	}

	@Override
	public ConversionWrapper getRelationOwnerPropertyConversion(String joinedEntityDelegatePropertyName) {
		return null;
	}

	@Override
	public ConversionWrapper getJoinedEntityPropertyConversion(String joinedEntityDelegatePropertyName) {
		return null;
	}

	private String getRelationOwnerDelegatePropertyName() {
		if (isLinkEntity) {
			return assignment.getKey().getProperty().getName();
		} else {
			return assignment.getLinkOtherKey().getName();
		}
	}

	private String getJoinedEntityDelegatePropertyName() {
		if (isLinkEntity) {
			return assignment.getLinkKey().getName();
		} else {
			return assignment.getOtherKey().getProperty().getName();
		}
	}

	public String getLinkIndexPropertyName() {
		return ((OrderedLinkPropertyAssignment) assignment).getLinkIndex().getName();
	}

	public static enum Level {
		linkEntity,
		otherEntity,
	}

	@Override
	public boolean getForceExternalJoin() {
		return true;
	}
}
