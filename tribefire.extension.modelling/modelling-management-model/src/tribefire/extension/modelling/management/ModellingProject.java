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
package tribefire.extension.modelling.management;

import java.util.List;

import com.braintribe.model.artifact.processing.ArtifactIdentification;
import com.braintribe.model.generic.annotation.meta.Unique;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ModellingProject extends ArtifactIdentification {
	
	EntityType<ModellingProject> T = EntityTypes.T(ModellingProject.class);
	
	@Unique
	String getName();
	void setName(String name);

	List<String> getAssets();
	void setAssets(List<String> assets);
	
	List<String> getModels();
	void setModels(List<String> models);
	
	String getAccessId();
	void setAccessId(String accessId);
	
}
