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
package com.braintribe.devrock.mungojerry.dialog.tab;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.braintribe.build.gwt.GwtModule;
import com.braintribe.build.gwt.ModuleCheckProtocol;

public interface AnalysisController {
	
	Collection<ModuleCheckProtocol> getProtocols();
	void setProtocols(Collection<ModuleCheckProtocol> protocols);
	
	File getOutputFolder();
	void setOutputFolder( File folder);
	
	File getSourceFolder();
	void setSourceFolder( File folder);	
	
	IProject getProject();
	void setProject( IProject project);
	
	List<File> getClasspathAsFiles();
	void setClasspathAsFiles( List<File> files);
		
	List<GwtModule> getModules();
	void setModules( List<GwtModule> modules);
	
}
