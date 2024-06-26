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
package com.braintribe.devrock.artifactcontainer.views.dependency.tabs.capability.clipboard;

import java.util.function.Function;

/**
 * a) a marker interface to declare that the implementer can produce meaningful contents for a transfer to the clip board<br/>
 * b) enables the use of the {@link Function} function provide( {@link ClipboardContentsProviderMode});<br/>
 * c) tells what {@link ClipboardContentsProviderMode} the implementer supports, even if what the actual mode means for the provider is left to the provider, 
 * so that the view can decide whether the action (and icon) should be enabled or not 
 * 
 * @author pit
 *
 */
public interface ClipboardContentsProviderCapable extends Function<ClipboardContentsProviderMode, String>{
	/**
	 * tells whether the implementer supports the specific mode, as defined in {@link ClipboardContentsProviderMode}
	 * @param mode - the {@link ClipboardContentsProviderMode} value to test 
	 * @return - true if it supports it, false otherwise 
	 */
	public boolean supportsMode( ClipboardContentsProviderMode mode);
}
