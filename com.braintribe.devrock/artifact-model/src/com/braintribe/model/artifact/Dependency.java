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
package com.braintribe.model.artifact;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.artifact.version.VersionRange;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.HasMetaData;

/**
 * a dependency .. is more or less a dependency as it appears in the pom, but has additional flags and data used in the
 * analysis or walks.
 * 
 * @author pit
 */
public interface Dependency extends Identification, HasMetaData {

	EntityType<Dependency> T = EntityTypes.T(Dependency.class);

	VersionRange getVersionRange();
	void setVersionRange(VersionRange versionRange);

	Set<Exclusion> getExclusions();
	void setExclusions(Set<Exclusion> exclusions);

	Set<Solution> getSolutions();
	void setSolutions(Set<Solution> solutions);

	boolean getOptional();
	void setOptional(boolean optional);

	/** use {@link #getType()} */
	@Deprecated
	String getPackagingType();
	@Deprecated
	void setPackagingType(String type);

	/** use {@link #getType()} */
	@Deprecated
	String getDependencyType();
	@Deprecated
	void setDependencyType(String type);

	/** the dependency, i.e. the content of the tag type in the pom */
	String getType();
	void setType(String type);

	String getScope();
	void setScope(String scope);

	boolean getUndetermined();
	void setUndetermined(boolean flag);

	boolean getUnresolved();
	void setUnresolved(boolean flag);

	Set<Artifact> getRequestors();
	void setRequestors(Set<Artifact> artifact);

	Integer getPathIndex();
	void setPathIndex(Integer index);

	Integer getHierarchyLevel();
	void setHierarchyLevel(Integer level);

	boolean getExcluded();
	void setExcluded(boolean flag);

	Dependency getOverridingDependency();
	void setOverridingDependency(Dependency override);

	boolean getAutoDefined();
	void setAutoDefined(boolean flag);

	Set<Dependency> getMergeParents();
	void setMergeParents(Set<Dependency> mergeParents);

	String getGroup();
	void setGroup(String group);

	List<String> getTags();
	void setTags(List<String> tags);
	
	Map<String,String> getRedirectionMap();
	void setRedirectionMap( Map<String, String> redirectionMap);
	
	/**
	 * @return - returns true if this {@link Dependency} is marked as invalid (by MC for instance)
	 */
	boolean getIsInvalid();
	void setIsInvalid( boolean isInvalid);
	
	/**
	 * @return - if invalidated, the reason might be here .. 
	 */
	String getInvalidationReason();
	void setInvalidationReason( String reason);

}
