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
package com.braintribe.model.artifact.compiled;

import java.util.List;

import com.braintribe.model.artifact.declared.DeclaredDependency;
import com.braintribe.model.artifact.essential.DependencyIdentification;
import com.braintribe.model.artifact.essential.VersionedArtifactIdentification;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.version.FuzzyVersion;
import com.braintribe.model.version.Version;
import com.braintribe.model.version.VersionExpression;
import com.braintribe.model.version.VersionInterval;
import com.braintribe.model.version.VersionRange;

/**
 * a fully qualified dependency (as opposed to the {@link DeclaredDependency} which may be incomplete)
 * 
 * @author pit
 *
 */
public interface CompiledDependencyIdentification extends DependencyIdentification, CompiledTerminal {
	
	EntityType<CompiledDependencyIdentification> T = EntityTypes.T(CompiledDependencyIdentification.class);
	String version = "version";
	
	/**
	 * @return - the version as a {@link VersionExpression}
	 */
	VersionExpression getVersion();
	void setVersion( VersionExpression expression);

	
	@Override
	default String asString() {
		return asString(this);
	}
	
	static String asString(CompiledDependencyIdentification cdi) {
		StringBuilder sb = new StringBuilder();
		String groupId = cdi.getGroupId();
		String artifactId = cdi.getArtifactId();
		VersionExpression version = cdi.getVersion();
		sb.append(groupId != null ? groupId: "<n/a");
		sb.append(':');
		sb.append(artifactId != null ? artifactId: "<n/a");
		sb.append('#');
		sb.append(version != null? version.asString(): "<n/a>");
		return sb.toString();
	}
	
	/**
	 * @param dependencyIdentificationAsString - the string to parse 
	 * @return - a {@link CompiledDependencyIdentification}
	 */
	static CompiledDependencyIdentification parse(String dependencyIdentificationAsString) {
		return from(VersionedArtifactIdentification.parse(dependencyIdentificationAsString));		
	}
	
	/**
	 * rangifies a dependency (if neither range nor fuzzy version) 
	 * @param dependencyIdentificationAsString - the dependency string 
	 * @return - a {@link CompiledDependencyIdentification} with a {@link FuzzyVersion} or a {@link VersionRange}
	 */
	static CompiledDependencyIdentification parseAndRangify(String dependencyIdentificationAsString) {
		return parseAndRangify(dependencyIdentificationAsString, false);
	}

	static CompiledDependencyIdentification parseAndRangify(String dependencyIdentificationAsString, boolean orUseGivenRevision) {
		CompiledDependencyIdentification cdi = from(VersionedArtifactIdentification.parse(dependencyIdentificationAsString));

		VersionExpression ve = cdi.getVersion();
		if (orUseGivenRevision && ve instanceof Version && ((Version) ve).getRevision() != null)
			return cdi;

		List<VersionInterval> intervalList = ve.asVersionIntervalList();
		if (intervalList.size() != 1) {
			return cdi;
		}
		VersionInterval vi = intervalList.get(0);
		if (vi instanceof VersionRange || vi instanceof FuzzyVersion) {
			return cdi;			
		}
		FuzzyVersion fv = FuzzyVersion.from((Version)vi);
		cdi.setVersion(fv);
		return cdi;
	}
	
	/**
	 * @param versionedArtifactIdentification
	 * @return
	 */
	static CompiledDependencyIdentification from(VersionedArtifactIdentification versionedArtifactIdentification) {
		return create( versionedArtifactIdentification.getGroupId(), versionedArtifactIdentification.getArtifactId(), versionedArtifactIdentification.getVersion());				
	}
	
	/**
	 * @param compiledArtifactIdentification - a {@link CompiledArtifactIdentification}
	 * @return - a {@link CompiledDependencyIdentification}
	 */
	static CompiledDependencyIdentification from(CompiledArtifactIdentification compiledArtifactIdentification) {
		return create( compiledArtifactIdentification.getGroupId(), compiledArtifactIdentification.getArtifactId(), compiledArtifactIdentification.getVersion());				
	}
	
	static CompiledDependencyIdentification from(CompiledDependencyIdentification cdi) {
		return create(cdi.getGroupId(), cdi.getArtifactId(), cdi.getVersion());
	}
	
	/**
	 * @param grpId - group id
	 * @param artId - artifact id
	 * @param vers - version expression
	 * @return
	 */
	static CompiledDependencyIdentification create(String grpId, String artId, String vers) {
		return create(grpId, artId, VersionExpression.parse( vers));
	}
	
	/**
	 * @param grpId - group id
	 * @param artId - artifact id
	 * @param versionExpression - {@link VersionExpression}
	 * @return
	 */
	static CompiledDependencyIdentification create(String grpId, String artId, VersionExpression versionExpression) {
		CompiledDependencyIdentification cdi = CompiledDependencyIdentification.T.create();
		cdi.setGroupId( grpId);
		cdi.setArtifactId( artId);
		cdi.setVersion(versionExpression);
		return cdi;
	}
 
	
}
