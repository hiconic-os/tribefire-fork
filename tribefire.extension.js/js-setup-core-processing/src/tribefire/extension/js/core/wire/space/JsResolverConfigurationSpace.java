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
package tribefire.extension.js.core.wire.space;

import java.io.File;
import java.util.Collection;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.artifact.PartTuple;
import com.braintribe.ve.api.VirtualEnvironment;

import tribefire.extension.js.core.api.JsResolver;
import tribefire.extension.js.core.wire.contract.JsResolverConfigurationContract;

/**
 * the space that allows configuration of the {@link JsResolver}
 * @author pit
 *
 */
public class JsResolverConfigurationSpace implements JsResolverConfigurationContract {

	private File workingDirectory;
	private File resolutionDirectory;
	private File m2Repository;
	private Collection<PartTuple> relevantPartTuples;
	private VirtualEnvironment virtualEnvironment;
	private boolean supportLocalProjects;
	private boolean preferMinOverPretty;
	private boolean useSymbolicLink;
	
	@Required @Configurable
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	@Override
	public File workingDirectory() {
		return workingDirectory;
	}
	
	@Configurable
	public void setResolutionDirectory(File resolutionDirectory) {
		this.resolutionDirectory = resolutionDirectory;
	}
	@Override
	public File resolutionDirectory() {
		return resolutionDirectory;
	}
	
	
	@Configurable
	public void setM2Repository(File m2Repository) {
		this.m2Repository = m2Repository;
	}
	@Override
	public File m2Repository() {		
		return m2Repository;
	}
	
	@Configurable
	public void setRelevantPartTuples(Collection<PartTuple> relevantPartTuples) {
		this.relevantPartTuples = relevantPartTuples;
	}
	@Override
	public Collection<PartTuple> relevantPartTuples() {	
		return relevantPartTuples;
	}
	
	@Configurable @Required
	public void setVirtualEnvironment(VirtualEnvironment virtualEnvironment) {
		this.virtualEnvironment = virtualEnvironment;
	}
	@Override
	public VirtualEnvironment virtualEnvironment() {	
		return virtualEnvironment;
	}
	
	@Configurable
	public void setPreferMinOverPretty(boolean preferMinOverPretty) {
		this.preferMinOverPretty = preferMinOverPretty;
	}
	@Override
	public boolean preferMinOverPretty() {	
		return preferMinOverPretty;
	}
	
	@Configurable
	public void setSupportLocalProjects(boolean supportLocalProjects) {
		this.supportLocalProjects = supportLocalProjects;
	}
	@Override
	public boolean supportLocalProjects() {
		return supportLocalProjects;
	}
	
	@Configurable
	public void setUseSymbolicLink(boolean useSymbolicLink) {
		this.useSymbolicLink = useSymbolicLink;
	}
	@Override
	public boolean useSymbolicLink() {
		return useSymbolicLink;
	} 
	
	
	

}
