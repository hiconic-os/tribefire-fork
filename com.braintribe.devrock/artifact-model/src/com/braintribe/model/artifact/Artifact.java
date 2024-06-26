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

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.braintribe.model.artifact.version.Version;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
 
/**
 * 
 * an artifact is an artifact is an artifact
 * 
 * it has more than needed in the pom, as we have some information added
 * 
 * type : describes the different types of an artifact
 * declaration type : describes how the dependencies are to be treated. 
 * 
 * @author pit
 *
 */

public interface Artifact extends Identification {
	
	final EntityType<Artifact> T = EntityTypes.T(Artifact.class);
	
	String version = "version";
	String terminal = "terminal";
	String dependencies = "dependencies";
	String parts = "parts";
	String managedDependencies = "managedDependencies";
	String artifactType = "artifactType";
	String declarationType = "declarationType";
	String releaseRevision = "releaseRevision";
	String releaseStatus = "releaseStatus";
	String exclusions = "exclusions";
	String updateStamp = "updateStamp";
	String dominants = "dominants";
	String packaging = "packaging";
	String properties = "properties";
	String parent = "parent";
	String resolvedParent = "resolvedParent";
	String resolved = "resolved";
	String archetype = "archetype";
	String redirection = "redirection";
	String licenses = "licenses";
	String imported = "imported";

	
	
	Version getVersion();
	void setVersion( Version version);
	
	boolean getTerminal();
	void setTerminal( boolean flag);
	
	List<Dependency> getDependencies();
	void setDependencies( List<Dependency> dependencies);
	
	List<Dependency> getManagedDependencies();
	void setManagedDependencies( List<Dependency> dependencies);
	
	Set<Part> getParts();
	void setParts( Set<Part> parts);
	
	ArtifactType getArtifactType();
	void setArtifactType( ArtifactType type);
	
	ArtifactDeclarationType getDeclarationType();
	void setDeclarationType( ArtifactDeclarationType type);
	
	String getReleaseRevision();
	void setReleaseRevision(String revision);
	
	ReleaseStatus getReleaseStatus();
	void setReleaseStatus( ReleaseStatus status);
	
	Date getUpdateStamp();
	void setUpdateStamp( Date date);	
	
	Set<Exclusion> getExclusions();
	void setExclusions( Set<Exclusion> dependencies);
	
	Set<Identification> getDominants();
	void setDominants( Set<Identification> dependencies);
	
	String getPackaging();
	void setPackaging( String packaging);	 
	
	Set<Property> getProperties();
	void setProperties( Set<Property> properties);
	
	Dependency getParent();
	void setParent( Dependency dependency);
	
	Solution getResolvedParent();
	void setResolvedParent( Solution parent);
	
	Set<Solution> getImported();
	void setImported( Set<Solution> imported);
	
	Solution getRedirection();
	void setRedirection( Solution redirection);
	
	boolean getResolved(); 
	void setResolved( boolean resolved); 
	
	Set<License> getLicenses();
	void setLicenses( Set<License> licenses);
	
	String getArchetype();
	void setArchetype( String archetype);
	
}
