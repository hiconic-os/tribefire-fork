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
package com.braintribe.devrock.zed.api.context;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.function.Predicate;

import com.braintribe.devrock.zed.api.core.ArtifactRegistry;
import com.braintribe.devrock.zed.api.core.CachingZedRegistry;
import com.braintribe.devrock.zed.api.core.ZedEntityResolver;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.zarathud.model.data.Artifact;

/**
 * interface for all contexts
 * @author pit
 *
 */
public interface CommonZedCoreContext {
	/**
	 * @return
	 */
	Artifact terminalArtifact();
	/**
	 * @return
	 */
	ArtifactRegistry artifacts();
	/**
	 * @return
	 */
	GmSession session();
	/**
	 * @return
	 */
	Map<URL, Artifact> urlToArtifactMap();
	/**
	 * @return
	 */
	ZedEntityResolver resolver();
	/**
	 * @return
	 */
	Predicate<Artifact> validImplicitArtifactReferenceFilter();
	/**
	 * @return
	 */
	URLClassLoader classloader();
	/**
	 * @return
	 */
	CachingZedRegistry registry();
}
