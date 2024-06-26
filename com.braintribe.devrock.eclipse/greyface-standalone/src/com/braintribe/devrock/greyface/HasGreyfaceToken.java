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
package com.braintribe.devrock.greyface;

public interface HasGreyfaceToken {
	// The plug-in ID
	static final String PLUGIN_ID = "com.braintribe.devrock.Greyface"; //$NON-NLS-1$
	final static String VIEW_ID = "com.braintribe.devrock.greyface.GreyfaceView";

	static final String PLUGIN_DEBUG = "GF_DEBUG";		
	//
	static final String PLUGIN_RESOURCE_PREFIX = "platform:/plugin/" + PLUGIN_ID;
}
