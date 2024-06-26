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
package com.braintribe.model.workbench;

import java.util.List;

import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.session.GmSession;

@SelectiveInformation("${displayName} Perspective")
public interface WorkbenchPerspective extends GenericEntity {

	EntityType<WorkbenchPerspective> T = EntityTypes.T(WorkbenchPerspective.class);

	@Mandatory
	String getName();
	void setName(String value);

	@Mandatory
	LocalizedString getDisplayName();
	void setDisplayName(LocalizedString value);

	List<Folder> getFolders();
	void setFolders(List<Folder> value);
	
	default WorkbenchPerspective workbenchPerspective(String name) {
		return initWorkbenchPerspective(name, name);
	}
	
	default WorkbenchPerspective initWorkbenchPerspective(String name, String displayName) {
		GmSession session = session();
		if (session != null)
			setDisplayName(session.create(LocalizedString.T).putDefault(displayName));
		else
			setDisplayName(LocalizedString.create(displayName));
			
		setName(name);
		return this;
	}
	
	default WorkbenchPerspective initWorkbenchPerspective(String name, LocalizedString displayName) {
		setDisplayName(displayName);
		setName(name);
		return this;
	}
	
	static WorkbenchPerspective create(String name, String displayName) {
		return T.create().initWorkbenchPerspective(name, displayName);
	}
	
	static WorkbenchPerspective create(String name) {
		return T.create().initWorkbenchPerspective(name, name);
	}
	
	static WorkbenchPerspective create(String name, LocalizedString displayName) {
		return T.create().initWorkbenchPerspective(name, displayName);
	}
}
