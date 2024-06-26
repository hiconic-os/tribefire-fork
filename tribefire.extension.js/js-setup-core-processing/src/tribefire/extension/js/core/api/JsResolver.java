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
package tribefire.extension.js.core.api;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import tribefire.extension.js.core.impl.JsResolverResult;

/**
 * the actual resolver
 * @author pit
 *
 */
public interface JsResolver {
	/**
	 * @param workingDirectory - the {@link File} pointing to the working folder
	 * @param jsRepository - the {@link File} pointing to the js-repository folder
	 */
	JsResolverResult resolve(File workingDirectory, File jsRepository);
	
	JsResolverResult resolve(File workingDirectory, File jsRepository, Map<File, String> linkMap);

	void resolve(Collection<String> terminals, File jsRepository, File targetDirectory, File projectsDirectory);	
}
