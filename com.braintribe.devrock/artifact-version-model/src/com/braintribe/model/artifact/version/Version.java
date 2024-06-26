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
package com.braintribe.model.artifact.version;

import java.util.List;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface Version extends StandardIdentifiable {

	final EntityType<Version> T = EntityTypes.T(Version.class);

	public List<VersionPart> getVersionData();
	public void setVersionData(List<VersionPart> data);

	public String getClassifier();
	public void setClassifier(String classifier);

	public boolean getSnapshot();
	public void setSnapshot(boolean snapshotFlag);

	public boolean getUndefined();
	public void setUndefined(boolean undefined);

	public String getOriginalVersionString();
	public void setOriginalVersionString(String versionAsString);

}
