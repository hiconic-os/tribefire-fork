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
package com.braintribe.gwt.gmview.client.parse;

import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class ParserArgument {
	
	private String value;
	private TypeCondition typeCondition;
	private int limit;
	private int offset;
	private PersistenceGmSession gmSession;
	private boolean simplifiedAssignment;

	public ParserArgument(String value, TypeCondition typeCondition) {
		this.value = value;
		this.typeCondition = typeCondition;
	}
	
	public ParserArgument(String value, TypeCondition typeCondition, int limit, int offset, PersistenceGmSession gmSession) {
		this.value = value;
		this.typeCondition = typeCondition;
		this.limit = limit;
		this.offset = offset;
		this.gmSession = gmSession;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TypeCondition getTypeCondition() {
		return typeCondition;
	}

	public void setTypeCondition(TypeCondition typeCondition) {
		this.typeCondition = typeCondition;
	}

	public boolean hasValue() {
		return value != null && !value.trim().isEmpty();
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public PersistenceGmSession getGmSession() {
		return gmSession;
	}
	
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	public boolean isSimplifiedAssignment() {
		return simplifiedAssignment;
	}
	
	public void setSimplifiedAssignment(boolean simplifiedAssignment) {
		this.simplifiedAssignment = simplifiedAssignment;
	}
	
}
