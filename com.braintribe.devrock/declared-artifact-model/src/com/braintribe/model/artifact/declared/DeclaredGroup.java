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
package com.braintribe.model.artifact.declared;

import java.util.List;
import java.util.Map;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * data structure for a kind of 'extraction/analysis'..
 * as it's using only 'declared' entities it's in this model, even 
 * if one may argue that it actually should be in the 'analysis' model rather 
 * @author pit
 *
 */
public interface DeclaredGroup extends GenericEntity {
		
	EntityType<DeclaredGroup> T = EntityTypes.T(DeclaredGroup.class);
	
	String groupId = "groupId";
	String location = "location";
	String groupParent = "groupParent";
	String members = "members";
	String memberCount = "memberCount";
	String groupDependencies = "groupDependencies";
	String failure = "failure";
	String major = "major";
	String minor = "minor";

	/**
	 * @return - the groupdId of the group analyzed
	 */
	String getGroupId();
	void setGroupId(String value);
	
	Integer getMajor();
	void setMajor(Integer value);
	
	Integer getMinor();
	void setMinor(Integer value);
	

	
	/**
	 * @return - the fully qualified path to the directory of the group
	 */
	String getLocation();
	void setLocation(String value);
	
	/**
	 * @return - the {@link DeclaredArtifact} that is the parent (named parent at least)
	 */
	DeclaredArtifact getGroupParent();
	void setGroupParent(DeclaredArtifact value);


	/**
	 * @return - a {@link List} of {@link DeclaredArtifact} that are all the artifacts in the group
	 */
	List<DeclaredArtifact> getMembers();
	void setMembers(List<DeclaredArtifact> value);
	
	Integer getMemberCount();
	void setMemberCount(Integer value);

	

	/**
	 * @return - a {@link Map} of referenced groups with the version (range) they were accessed
	 */
	Map<String, String> getGroupDependencies();
	void setGroupDependencies(Map<String, String> value);
	
	/**
	 * @return - a {@link Reason} that reflects that something went wrong (or there are issues in the group)
	 */
	Reason getFailure();
	void setFailure(Reason value);

	/**
	 * @return - true if some reasons are in the failed property
	 */
	default boolean hasFailed() {
		return getFailure() != null;
	}

}
