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
package tribefire.extension.js.core.impl;


import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.braintribe.cfg.Configurable;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.artifact.processing.part.PartTupleProcessor;
import com.braintribe.ve.api.VirtualEnvironment;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

import tribefire.extension.js.core.api.JsResolver;
import tribefire.extension.js.core.wire.JsResolverTerminalModule;
import tribefire.extension.js.core.wire.contract.JsResolverContract;
import tribefire.extension.js.core.wire.space.JsResolverConfigurationSpace;

/**
 * core processor for js module resolving support
 * @author pit
 *
 */
public class JsResolvingProcessor {	
	
	private static final String USER_HOME = "user.home";
	private static final String JS_LIBRARIES_DEFAULT = "/.devrock/js-libraries";
	public static final String JS_LIBRARIES = "DEVROCK_JS_LIBRARIES";
	private boolean preferMinOverPretty;
	private boolean supportLocalProjects;	
	private boolean useSymbolicLink = true;
		
	@Configurable
	public void setPreferMinOverPretty(boolean preferMinOverPretty) {
		this.preferMinOverPretty = preferMinOverPretty;
	}
	@Configurable
	public void setSupportLocalProjects(boolean supportLocalProjects) {
		this.supportLocalProjects = supportLocalProjects;
	}
	
	@Configurable
	public void setUseSymbolicLink(boolean useSymbolicLink) {
		this.useSymbolicLink = useSymbolicLink;
	}
	
	public JsResolverResult resolve(File workingDirectory, Map<File, String> linkFolders, VirtualEnvironment ves) {
		if (workingDirectory == null) {
			workingDirectory = new File( ".");
		}
										
		// create a specific configuration space 
		JsResolverConfigurationSpace configurationSpace = new JsResolverConfigurationSpace();
		configurationSpace.setWorkingDirectory(workingDirectory.getParentFile());
		configurationSpace.setResolutionDirectory(workingDirectory.getParentFile());
		configurationSpace.setRelevantPartTuples( Arrays.asList( PartTupleProcessor.createPomPartTuple()));
		configurationSpace.setVirtualEnvironment(ves);
		configurationSpace.setPreferMinOverPretty(preferMinOverPretty);
		configurationSpace.setSupportLocalProjects(supportLocalProjects);		
		configurationSpace.setUseSymbolicLink( useSymbolicLink);
				
		try (
				WireContext<JsResolverContract> resolverContext = Wire.context( new JsResolverTerminalModule( configurationSpace))
		) {
			JsResolver resolver = resolverContext.contract().jsResolver();
			File jsRepository = determineJsRepository(ves);
			
			if (linkFolders != null)
				return resolver.resolve(workingDirectory, jsRepository, linkFolders);
			else
				return resolver.resolve(workingDirectory, jsRepository);			
						
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot execute js resolving", IllegalStateException::new);
		} 						

	}
		
	/**
	 * @param workingDirectoryPath - the directory of the project to instrument
	 * @param settingsPath - the path to the settings.xml file to use (null if standard Maven style resolving should happen) 
	 * @param m2RepositoryPath - the path to Maven local repository (null if to be taken from the settings.xml)
	 */
	public JsResolverResult resolve(File workingDirectory, VirtualEnvironment ves) {
		return resolve(workingDirectory, null, ves);
	}
	
	private File determineJsRepository(VirtualEnvironment ves) {
		String jsRepositoryPath = ves.getEnv(JS_LIBRARIES);
		if (jsRepositoryPath == null) {
			String userhome = ves.getProperty(USER_HOME);
			jsRepositoryPath = userhome + JS_LIBRARIES_DEFAULT;
		}
		
		File jsRepository = new File( jsRepositoryPath);
		jsRepository.mkdirs();
		return jsRepository;
	}
	
	/**
	 * @param workingDirectoryPath - the directory of the project to instrument
	 * @param settingsPath - the path to the settings.xml file to use (null if standard Maven style resolving should happen) 
	 * @param m2RepositoryPath - the path to Maven local repository (null if to be taken from the settings.xml)
	 */

	public void resolve(Collection<String> terminals, File targetDirectory, File projectsDirectory, VirtualEnvironment ves) {							
		// create a specific configuration space 
		JsResolverConfigurationSpace configurationSpace = new JsResolverConfigurationSpace();
		configurationSpace.setWorkingDirectory( targetDirectory);	
		configurationSpace.setResolutionDirectory(projectsDirectory);
		configurationSpace.setRelevantPartTuples( Arrays.asList( PartTupleProcessor.createPomPartTuple()));
		configurationSpace.setVirtualEnvironment(ves);
		configurationSpace.setPreferMinOverPretty(preferMinOverPretty);
		configurationSpace.setSupportLocalProjects(supportLocalProjects);
		configurationSpace.setUseSymbolicLink(useSymbolicLink);
		
		try (
			WireContext<JsResolverContract> resolverContext = Wire.context( new JsResolverTerminalModule( configurationSpace))
		) {
			
			File jsRepository = determineJsRepository(ves);			
			JsResolver resolver = resolverContext.contract().jsResolver();	
			jsRepository.mkdirs();			
			resolver.resolve(terminals, jsRepository, targetDirectory, projectsDirectory);			
						
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot execute js resolving", IllegalStateException::new);
		} 						
	}	
}
