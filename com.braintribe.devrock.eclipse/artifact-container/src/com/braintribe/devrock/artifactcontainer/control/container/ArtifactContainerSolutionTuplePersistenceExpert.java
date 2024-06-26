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
package com.braintribe.devrock.artifactcontainer.control.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;

import com.braintribe.codec.marshaller.api.ConfigurableGmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerPlugin;
import com.braintribe.devrock.artifactcontainer.ArtifactContainerStatus;
import com.braintribe.devrock.artifactcontainer.container.ArtifactContainer;
import com.braintribe.model.malaclypse.cfg.container.ArtifactContainerSolutionTuple;
import com.braintribe.model.malaclypse.container.ContainerPersistence;
import com.braintribe.utils.IOTools;

/**
 * expert to read/write the {@link ArtifactContainerSolutionTuple} of a {@link ArtifactContainer}
 * @author pit
 *
 */
public class ArtifactContainerSolutionTuplePersistenceExpert {		
	static final String DEPENDENCY_FILE = ".artifact.container.dependencies.xml";
	private StaxMarshaller marshaller = new StaxMarshaller();	
	/**
	 * get the proper file location 	
	 */
	private File deriveFile( IProject project) {
		String path = ArtifactContainerPlugin.getInstance().getStateLocation().toOSString();
		String name = path + File.separator + project.getName() + DEPENDENCY_FILE;
		return new File( name);
	}
	
	public void dropFile(IProject project) {
		File file = deriveFile(project);
		file.deleteOnExit();
	}
	/**
	 * decode aka read the persisted data of the {@link ArtifactContainer} attached to {@link IJavaProject}
	 * @param project - the {@link IJavaProject} the container's attached to 
	 * @return - the {@link ArtifactContainerSolutionTuple} found 
	 */
	public ContainerPersistence decode(IProject project) {
		File file = deriveFile( project);
		if (!file.exists()) {		
			return null;
		}
		
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);						
			ContainerPersistence tuple = (ContainerPersistence) marshaller.unmarshall(stream);
			return tuple;
		} catch (MarshallException e) {
			String msg="cannot unmarshall persisted container data from [" + file.getAbsolutePath() + "] for project [" + project.getName() + "]"; 
			
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			ArtifactContainerPlugin.getInstance().log(status);	
			return null;
		} catch (FileNotFoundException e) {
			String msg="cannot open stream to persisted container data from [" + file.getAbsolutePath() + "] for project [" + project.getName() + "]"; 			
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			ArtifactContainerPlugin.getInstance().log(status);	
			return null;
		}		
		finally {
			IOTools.closeQuietly(stream);
		}
	}
	
	/**
	 * store the {@link ArtifactContainer}'s {@link ArtifactContainerSolutionTuple}
	 * @param project - the {@link IJavaProject} the container is attached to 
	 * @param tuple - the {@link ArtifactContainerSolutionTuple}
	 */
	public void encode( IProject project, ContainerPersistence tuple){
		File file = deriveFile(project);		
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			ConfigurableGmSerializationOptions options = new ConfigurableGmSerializationOptions();
			options.setOutputPrettiness( OutputPrettiness.high);
			marshaller.marshall(stream, tuple, options);					
		} catch (MarshallException e) {
			String msg ="cannot marshall container data to [" + file.getAbsolutePath() + "]";		
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			ArtifactContainerPlugin.getInstance().log(status);	
		} catch (IOException e) {
			String msg ="cannot open stream to encoded container data to [" + file.getAbsolutePath() + "]";
			ArtifactContainerStatus status = new ArtifactContainerStatus(msg, e);
			ArtifactContainerPlugin.getInstance().log(status);	
		}
	}
	
}
