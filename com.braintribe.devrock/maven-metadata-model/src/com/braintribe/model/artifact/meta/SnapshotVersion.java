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
package com.braintribe.model.artifact.meta;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


/**
 * represents a snapshot version 
 * @author pit
 *
 */
public interface SnapshotVersion extends GenericEntity{
	
	final EntityType<SnapshotVersion> T = EntityTypes.T(SnapshotVersion.class);
	
	/**
	 * @return - {@link Date} when updated
	 */
	Date getUpdated();
	void setUpdated( Date updated);
	
	/**
	 * @return - classifer
	 */
	String getClassifier();
	void setClassifier( String classifier);
	
	/**
	 * @return - extension
	 */
	String getExtension();
	void setExtension( String extension);
	
	/**
	 * @return - referencing value
	 */
	String getValue();
	void setValue(String value);
	
}
