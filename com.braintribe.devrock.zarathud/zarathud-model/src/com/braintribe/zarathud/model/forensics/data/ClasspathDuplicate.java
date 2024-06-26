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
package com.braintribe.zarathud.model.forensics.data;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * @author pit
 *
 */
public interface ClasspathDuplicate extends GenericEntity{

	EntityType<ClasspathDuplicate> T = EntityTypes.T(ClasspathDuplicate.class);
	
	String type = "type";
	String duplicates = "duplicates";
	String referencer = "referencers";
	String shadowingRuntime = "shadowingRuntime";

	/**
	 * @return - the {@link ZedEntity} representing the type 
	 */
	ZedEntity getType();
	void setType(ZedEntity type);
	
	/**
	 * @return - a {@link List} of {@link Artifact} that declare the type 
	 */
	List<Artifact> getDuplicates();
	void setDuplicates(List<Artifact> duplicates);
	
	/**
	 * @return - the {@link ZedEntity} of the terminal that references this type
	 */
	List<ZedEntity> getReferencersInTerminal();
	void setReferencersInTerminal(List<ZedEntity> values);

	
	/**
	 * @return - 
	 */
	boolean getShadowingRuntime();
	void setShadowingRuntime(boolean shadowinRuntime);
	
	default String toStringRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append( getType().getName());
		sb.append("(");
		boolean first = true;
		for (Artifact duplicate : getDuplicates()) {
			if (first) {
				first = false;
			}
			else {
				sb.append(",");
			}
			sb.append( duplicate.toVersionedStringRepresentation());
		}
		return sb.toString();
	}
		
	
	
}
