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
package tribefire.extension.js.model.api.request;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.service.api.result.Neutral;

@PositionalArguments({"projectPath"})
@Description("Assembles transitive dependencies for a given project into its /lib folder."
		+ "A project is identified by its pom.xml. This request is workspace-aware. In case the parent directory from where the request "
		+ "is called contains file 'js-workspace.yaml', local projects will be preferred over the js-libraries repository if they satisfy a dependency. "
		+ "Symbolic links are used for referring to respective sources. A project links to its own source folder within its /lib folder, also via symbolic link. "
		+ "The symbolic link folder names represent potential version uncertainties of respective dependencies.")
public interface AssembleJsDeps extends JsSetupRequest {
	
	EntityType<AssembleJsDeps> T = EntityTypes.T(AssembleJsDeps.class);

	@Alias("min")
	@Description("Prefer minified over pretty package, if given.")
	boolean getPreferMinOverPretty();
	void setPreferMinOverPretty(boolean preferMinOverPretty);
	
	@Initializer("'.'")
	@Mandatory
	@Description("The path to the project containing the pom.xml which dependencies should be assembled transitively.")
	String getProjectPath();
	void setProjectPath(String projectPath);
	
	@Override
	EvalContext<? extends Neutral> eval(Evaluator<ServiceRequest> evaluator);
	
}
