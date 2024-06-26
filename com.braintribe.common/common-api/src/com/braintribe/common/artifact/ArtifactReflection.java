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
package com.braintribe.common.artifact;

import java.util.Set;

/**
 * One artifact is fully described by specifying its <b>groupId</b>, <b>artifactId</b>, <b>version</b> and in addition its <b>archetype(s)</b>. 
 * From those informations the name is determined to be "groupId:artifactId" and the versioned-name to be "groupId:artifactId#versioned".   
 * 
 * @author Dirk Scheffler
 *
 */
public interface ArtifactReflection {

	String groupId();

	String artifactId();

	String version();

	Set<String> archetypes();

	/**
	 * @return "groupId:artifactId"
	 */
	String name();

	/**
	 * @return "groupId:artifactId#version"
	 */
	String versionedName();
}