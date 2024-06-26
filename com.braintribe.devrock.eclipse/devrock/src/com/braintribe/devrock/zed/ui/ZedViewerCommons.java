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
package com.braintribe.devrock.zed.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import com.braintribe.devrock.api.project.JavaProjectDataExtracter;
import com.braintribe.devrock.api.ui.editors.support.EditorSupport;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.devrock.plugin.DevrockPluginStatus;
import com.braintribe.gm.model.reason.Maybe;

/**
 * some commons for the different viewers 
 * 
 * @author pit
 *
 */
public class ZedViewerCommons {
	/**
	 * open a source file corresponding to the type's data 
	 * @param packageName - the package name of the type 
	 * @param typeName - the name of the type 
	 */
	public static void openFile(ZedViewingContext context, String packageName, String typeName) {
		IProject project = context.getProject();
		// extract the source directories 
		Maybe<List<File>> maybeSourceDirectories = JavaProjectDataExtracter.getSourceDirectories(project);
		List<File> sourceDirectories = new ArrayList<>();
		if (maybeSourceDirectories.isUnsatisfied()) {
			DevrockPluginStatus status = new DevrockPluginStatus("cannot extract location of the source directories of:" + project.getName(), IStatus.ERROR);
			DevrockPlugin.instance().log(status);
			return;
		}
		else if (maybeSourceDirectories.isIncomplete()) {
			DevrockPluginStatus status = new DevrockPluginStatus("cannot extract location of ALL source directories of:" + project.getName(), IStatus.WARNING);
			DevrockPlugin.instance().log(status);
			sourceDirectories = maybeSourceDirectories.value();
		}
		else {
			sourceDirectories = maybeSourceDirectories.get();
		}
		// iterate over it 
		for (File file : sourceDirectories) {
			IPath location = project.getLocation();
			IPath filePath = new Path( file.getAbsolutePath());
			IPath relativePath = filePath.makeRelativeTo(location);
			String fileName = relativePath.toOSString() + "/" + packageName.replace('.', '/') + "/" + typeName + ".java";
		
			/*
			File file2 = new File( fileName);
			if (file2.exists()) {
				EditorSupport.load(file2);
			}
			*/
			
			IResource resource = project.findMember(fileName);
			// if a resource's found, we show it and return..
			if (resource != null) {
				EditorSupport.load( resource);
				break;
			}
			
		}		
	}
}
