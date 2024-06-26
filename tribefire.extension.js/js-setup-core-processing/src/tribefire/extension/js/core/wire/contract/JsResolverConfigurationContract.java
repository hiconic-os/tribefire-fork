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
package tribefire.extension.js.core.wire.contract;

import java.io.File;
import java.util.Collection;

import com.braintribe.model.artifact.PartTuple;
import com.braintribe.ve.api.VirtualEnvironment;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.js.core.api.JsResolver;

/**
 * the one and only configuration contract for the {@link JsResolver}
 * @author pit
 *
 */
public interface JsResolverConfigurationContract extends WireSpace {

	/**
	 * @return - the working directory, i.e. where the project to instrument lies
	 */
	File workingDirectory();
	
	File resolutionDirectory();
	/**
	 * @return - the local maven repository (default is null -> taken from settings.xml)
	 */
	File m2Repository();
	
	/**
	 * @return - the parts to download (currently :pom, :zip, min:zip, asset:man)
	 */
	Collection<PartTuple> relevantPartTuples();

	/**
	 * @return - the virtual environment to use 
	 */
	VirtualEnvironment virtualEnvironment();
	
	/**
	 * @return - true if 'min' (alternative part, 'min:zip') instead of 'pretty' (standard part, ':zip') are to be used  
	 */
	default boolean preferMinOverPretty() {return false;}
	
	/**
	 * @return - true if local projects should be resvolved and linked
	 */
	default boolean supportLocalProjects() {return false;}
	
	/**
	 * @return - creates symbolic links if true or copies the files if false
	 */
	default boolean useSymbolicLink() {return true;}

	
}
