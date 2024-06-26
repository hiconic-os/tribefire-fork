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
package com.braintribe.devrock.zed.forensics.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.zarathud.model.data.Artifact;

/**
 * an entry in the {@link DependencyStructureRegistry}, containing all tags a dependency/solution has been adorned with
 * @author pit
 *
 */
public class DependencyStructureEntry {
	private String name;
	private Map<String, List<String>> requestorToTagMap = new HashMap<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, List<String>> getRequestorToTagMap() {
		return requestorToTagMap;
	}
	public void setRequestorToTagMap(Map<String, List<String>> requestorToTagMap) {
		this.requestorToTagMap = requestorToTagMap;
	}
	
	public DependencyStructureEntry(String key) {
		name = key;
	}
	
	
	/**	  
	 * @param requester - the name of the requesting solution (as condensed name) 
	 * @return - a {@link List} with all collected tags of this requester
	 */
	public List<String> getDependencyTagViaRequestor( String requester) {
		return requestorToTagMap.get(requester);
	}
	
	/**
	 * @param artifact - the requesting artifact 
	 * @return - a {@link List} with all collected tags of this requester 
	 */
	public List<String> getDependencyTagViaRequestor( Artifact artifact) {
		return requestorToTagMap.get( artifact.toVersionedStringRepresentation());
	}
	
	/**
	 * @return - a {@link List} with *all* tags combined 
	 */
	public List<String> getAllDependencyTags() {
		Set<String> result = new HashSet<>();
		for (List<String> toAdd : requestorToTagMap.values()) {
			result.addAll( toAdd);
		}
		return new ArrayList<>(result);
	}
	
	
}
