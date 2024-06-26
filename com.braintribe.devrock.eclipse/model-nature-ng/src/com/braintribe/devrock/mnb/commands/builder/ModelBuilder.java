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
package com.braintribe.devrock.mnb.commands.builder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.ui.statushandlers.StatusManager;

import com.braintribe.build.model.ModelDeclarations;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.mnb.marker.ReasonedMarkerHandler;
import com.braintribe.devrock.mnb.plugin.ModelBuilderPlugin;
import com.braintribe.devrock.mnb.plugin.ModelBuilderStatus;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.gm.model.reason.Reason;

public class ModelBuilder extends IncrementalProjectBuilder {
	public static final String ID = "com.braintribe.devrock.mn.commands.builder.ModelBuilder";
	
	public void forceBuild(IProject project) { 
		try { 	
			internalBuild(project, FULL_BUILD, new HashMap<>(), null); 
		} catch (Exception e) { 
			String msg = "error building model declaration file on triggered";
			ModelBuilderStatus status = new ModelBuilderStatus(msg, e);
			ModelBuilderPlugin.instance().log(status); 
		} 
	}
	
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		try {
			IProject project = getProject();
			return internalBuild(project, kind, args, monitor);
		} catch (Exception e) {
			String msg = "error building model declaration file";
			ModelBuilderStatus status = new ModelBuilderStatus(msg, e);
			StatusManager.getManager().handle(status);
			throw e instanceof CoreException ? (CoreException) e : new CoreException( status);
		}
	}
	
	
	
	
	protected IProject[] internalBuild(IProject project, int kind, Map<String, String> args, IProgressMonitor monitor) throws Exception {

		
		IResourceDelta delta = getDelta(project);

		boolean full = kind == FULL_BUILD;
		
		boolean isRelevantChange = full || isRelevantDelta(delta);
		
		if (!isRelevantChange) {
			// no relevant change
			String msg = "no relevant change detected on model: " + project.getName();
			ModelBuilderStatus status = new ModelBuilderStatus(msg, Status.INFO);
			ModelBuilderPlugin.instance().log(status);
			return null;
		}
		
		if (project.hasNature(JavaCore.NATURE_ID)) {
			IJavaProject javaProject = JavaCore.create(project);
			String[] runtimeClasspathEntries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
			
			URL[] cp = convertClasspath(runtimeClasspathEntries);
						
			IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();
			
			List<File> outputLocations = getOutputLocations(javaProject);
			// target folder is first (and currently only) location here
			File targetFolder = outputLocations.get(0);
			
			for (IClasspathEntry entry: rawClasspath) {
				if (entry.getContentKind() == IClasspathEntry.CPE_SOURCE) {
					IPath outputLocation = entry.getOutputLocation();
					File outputLocationFile = ResourcesPlugin.getWorkspace().getRoot()
							.getFile(outputLocation).getRawLocation().toFile();
					outputLocations.add(outputLocationFile);
				}
			}
			runUpdateModelDeclaration(cp, outputLocations, project, targetFolder);
		}
		else {
			String msg = "project doesn't have model-nature: " + project.getName(); 
			ModelBuilderStatus status = new ModelBuilderStatus(msg, IStatus.WARNING);
			ModelBuilderPlugin.instance().log(status);
		}
		
		return null;
	}
	
	private List<File> getOutputLocations(IJavaProject javaProject) throws JavaModelException {
		List<File> outputLocations = new ArrayList<>();
		
		IPath wsOutputLocation = javaProject.getOutputLocation();
		IFile ifile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(wsOutputLocation);
		final File outputFolder = ifile.getRawLocation().toFile();
		outputLocations.add(outputFolder);
		return outputLocations;
	}
	
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		IJavaProject javaProject = JavaCore.create(project);
		List<File> outputLocations = getOutputLocations(javaProject);
		
		for (File outputLocation: outputLocations) {
			File modelDeclarationCandidate = new File(outputLocation, "model-declaration.xml");
			if (modelDeclarationCandidate.exists())
				modelDeclarationCandidate.delete();
		}
		super.clean(monitor);
	}

	private boolean isRelevantDelta(IResourceDelta delta) {
		// TODO : check incremental logic, currently always full blown build
		return true;
		
	}

	private void runUpdateModelDeclaration(URL[] cp, List<File> outputFolders, IProject project, File targetFolder) throws Exception {
		File pomFile;
		IResource pomResource = project.findMember("pom.xml");
		if (pomResource != null) {
			pomFile = pomResource.getLocation().toFile();
		}
		else {
			String msg = "project [" + project.getName() + "] has no pom";
			ModelBuilderStatus status = new ModelBuilderStatus(msg, IStatus.ERROR);
			StatusManager.getManager().handle(status);
			return;
		}

		// TODO : what to do with the warning?		
		Reason reason = ModelDeclarations.buildModelDeclaration( DevrockPlugin.mcBridge()::readPomFile, Arrays.asList(cp), outputFolders, pomFile, targetFolder);
		if (reason != null) {
			ReasonedMarkerHandler.addFailedResolutionMarkerToProject(getProject(), reason);
			ModelBuilderStatus status = new ModelBuilderStatus("cannot build model declaration for [" + project.getName() + "] with " + pomFile.getAbsolutePath() + "] as : " + reason.stringify(), IStatus.ERROR);
			ModelBuilderPlugin.instance().log(status);
		}
		else {
			boolean showSuccessMessage = DevrockPlugin.instance().storageLocker().getValue( StorageLockerSlots.SLOT_MB_SUCCESS_MESSAGES, false);
			if (showSuccessMessage) {			
				ModelBuilderStatus status = new ModelBuilderStatus(" successfully built model-declaration file for [" + project.getName() + "] to: " + targetFolder.getAbsolutePath() + "]", Status.INFO);
				ModelBuilderPlugin.instance().log(status);
			}
		}
	}

	private String toPath(List<File> outputFolders) {
		return outputFolders.stream()
				.map(f -> f.getAbsolutePath())
				.collect(Collectors.joining(File.pathSeparator));
	}

	private URL[] convertClasspath(String[] runtimeClasspathEntries) {
			URL cp[] = new URL[runtimeClasspathEntries.length];
			
			
			for (int i = 0; i < cp.length; i++) {
				IPath path = new Path(runtimeClasspathEntries[i]);
				File file = path.toFile();
				try {
					cp[i] = file.toURI().toURL();
				} catch (MalformedURLException e) {
					String msg = "error while converting classpath entry [" + file + "] to a url";
					ModelBuilderStatus status = new ModelBuilderStatus(msg, e);
					StatusManager.getManager().handle(status);
					throw new RuntimeException(msg, e);
				}
			}
			return cp;
	}
	
}

