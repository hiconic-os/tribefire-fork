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
package com.braintribe.zarathud.model.data;


import java.net.URL;
import java.util.List;


import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Transient;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * the basic entity within Z
 * @author pit
 *
 */
//@Abstract
public interface ZedEntity extends GenericEntity {
	
	final EntityType<ZedEntity> T = EntityTypes.T(ZedEntity.class);
	String definedInTerminal = "definedInTerminal";
	String key = "key";
	String name = "name";
	String desc = "desc";
	String moduleName = "moduleName";
	String artifact = "artifact";
	String scannedFlag = "scannedFlag";
	String directDependency = "directDependency";
	String resourceUrl = "resourceUrl";
	String scanData = "scanData";
	String isRuntime = "isRuntime";
	String isUnknown = "isUnknown";

	/**
	 * true if no references from outside the CP are found
	 * @return - whether the entity is only referenced from within the jar 
	 */
	boolean getDefinedInTerminal();
	void setDefinedInTerminal( boolean local);
	
	String getKey();
	void setKey( String key);
	
	/**
	 * the name of the entity 
	 * @return - the name 
	 */
	String getName();
	void setName( String name);
	
	/**
	 * the ASM desc of the entity 
	 * @return - the ASM desc
	 */
	String getDesc();
	void setDesc( String desc);
	
	/**
	 * @return
	 */
	String getModuleName();
	void setModuleName(String name);
	
	/**
	 * the {@link Artifact}
	 * @return - the {@link Artifact} where the entity has been defined 
	 */
	List<Artifact> getArtifacts();
	void setArtifacts( List<Artifact> artifact);
		
	/**
	 * true if it already has been scanned
	 * @return - true if has been scanned already, false otherwise 
	 */
	boolean getScannedFlag();	
	void setScannedFlag( boolean value);
	
	/**
	 * true if the entity is referenced directly by another of the terminal
	 * @return - whether the entity is referenced by another directly 
	 */
	boolean getDirectDependency();
	void setDirectDependency( boolean value);
	
	/**
	 * @return - the resource URL (as returned by the classloader)
	 */
	@Transient
	URL getResourceUrl();
	void setResourceUrl( URL resourceUrl);
	
	 @Transient
	 Object getScanData();
	 void setScanData( Object scandata);
	
	 /**
	 * @return - true if this type is a runtime thingi 
	 */
	boolean getIsRuntime();
	void setIsRuntime( boolean isRuntime);
	
	/**
	 * @return - true if this type is unknown
	 */
	boolean getIsUnknown();
	void setIsUnknown( boolean isUnknown);
	
	/**
	 * @return - true if qualified
	 */
	boolean getQualifiedFlag();
	void setQualifiedFlag(boolean b);
	
	/**
	 * @return - true if this {@link ZedEntity} was only partially (shallow) qualified
	 */
	boolean getInnersRequireResolving();
	void setInnersRequireResolving( boolean b);

}
