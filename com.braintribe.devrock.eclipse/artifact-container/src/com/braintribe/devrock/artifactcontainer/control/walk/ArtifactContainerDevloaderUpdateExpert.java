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
package com.braintribe.devrock.artifactcontainer.control.walk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

import com.braintribe.crypto.utils.TextUtils;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.devrock.artifactcontainer.container.ArtifactContainer;
import com.braintribe.plugin.commons.container.ContainerCommons;

/**
 * the expert that handles the devloader file and its content. 
 * 
 * @author pit
 *
 */
public class ArtifactContainerDevloaderUpdateExpert {
	private static final String DEVLOADER_FILE = ".#webclasspath_ac";

	/**
	 * update the devloader's file from the runtime entries of the container 
	 * @param container - the {@link ArtifactContainer}
	 */
	public static void updateTomcatDevloader( ArtifactContainer container) {
		IClasspathEntry [] launchEntries = container.getRuntimeClasspathEntries();		
		List<String> values = buildEntries( container.getProject(), launchEntries);
		String path = container.getProject().getProject().getLocation().toOSString();
		File devloaderFile = new File( path + File.separator + DEVLOADER_FILE);
		writeToWebclasspathFile(values, devloaderFile);
		ArtifactContainerPlugin.log("updated [" + devloaderFile.getAbsolutePath() + "] for container [" + container.getId() + "] attached to [" + container.getProject().getProject().getName() + "]");
		
	}
	
	public static boolean hasDevloader( IProject project) {
		String path = project.getLocation().toOSString();
		File devloaderFile = new File( path + File.separator + DEVLOADER_FILE);
		return devloaderFile.exists();
	}
	
	/**
	 * build a list of the string representations of the runtime classpath entries 
	 * @param project - the {@link IJavaProject} the container's linked to 
	 * @param entries - an array of {@link IClasspathEntry}, the runtime entries of the container 
	 * @return - a {@link List} of String representation (jar refereneces and project references) 
	 */
	private static List<String> buildEntries( IJavaProject project, IClasspathEntry [] entries){
		List<String> values = new ArrayList<String>();
		
		for (IClasspathEntry entry : entries) {
			String entryPath = null;
			switch (entry.getEntryKind()) {
				case IClasspathEntry.CPE_LIBRARY: {
					entryPath = entry.getPath().toOSString();
					break;
				}
				case IClasspathEntry.CPE_PROJECT: { 					
					try {
						entryPath = ContainerCommons.getOutputLocationOfProject( entry).getAbsolutePath();
					} catch (JavaModelException e) {
						String msg ="Cannot determine output directory of project [" + entry.getPath().toOSString() + "]";					
						ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
						ArtifactContainerPlugin.getInstance().log(status);	
					}
					break;
				}
				default: {
					break;
				}
			}
			if (entryPath != null) {
				values.add( entryPath);
			}
		}
		
		// add ourself to it
		try {
			IPath wsOutputLocation = project.getOutputLocation();			  
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(wsOutputLocation);
			File sourceFile = ifile.getRawLocation().toFile();
			values.add( sourceFile.getAbsolutePath());
			
		} catch (JavaModelException e) {
			String msg ="Cannot determine output directory of project [" + project.getElementName() + "]";			
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			ArtifactContainerPlugin.getInstance().log(status);	
		}
		return values;
	}
	
	
	/**
	 * make a backup and then write to the devloader file 
	 * @param values - the {@link List} of string values 
	 * @param file - the devloader file 
	 * @return - true if it worked, false otherwise, guess.. 
	 */
	private static boolean writeToWebclasspathFile( List<String> values, File file) {
		if (ContainerCommons.checkWriteable(file) == false)
			return false;
		
		ContainerCommons.backupFile(file);
		StringBuilder builder = new StringBuilder();
		for (String value : values) {
			if (builder.length() > 0) 
				builder.append("\n");
			builder.append( value);
		}
		TextUtils.writeContentsToFile( builder.toString(), file);
		return true;
	}
	
}
