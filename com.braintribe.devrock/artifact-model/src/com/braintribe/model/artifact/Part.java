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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * a part corresponds with an existing aspect of an artifact, i.e. its files
 * 
 * @author pit
 */
public interface Part extends Artifact {

	EntityType<Part> T = EntityTypes.T(Part.class);

	Date getUpdateTime();
	void setUpdateTime(Date date);

	String getPartName();
	void setPartName(String partName);

	PartTuple getType();
	void setType(PartTuple type);

	String getLocation();
	void setLocation(String location);

	String getMd5Hash();
	void setMd5Hash(String hash);

	String getSha1Hash();
	void setSha1Hash(String hash);

}
