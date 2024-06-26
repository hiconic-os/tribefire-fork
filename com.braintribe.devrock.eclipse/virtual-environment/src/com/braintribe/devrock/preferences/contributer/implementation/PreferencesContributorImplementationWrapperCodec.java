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
package com.braintribe.devrock.preferences.contributer.implementation;

import com.braintribe.cc.lcd.HashSupportWrapperCodec;
import com.braintribe.devrock.preferences.contributer.PreferencesContributerImplementation;

public class PreferencesContributorImplementationWrapperCodec extends HashSupportWrapperCodec<PreferencesContributerImplementation> {

	@Override
	protected boolean entityEquals(PreferencesContributerImplementation implementation1, PreferencesContributerImplementation implementation2) {		
		return implementation1.getName().equalsIgnoreCase(implementation2.getName());
	}

	@Override
	protected int entityHashCode(PreferencesContributerImplementation implementation) {		
		return implementation.getName().hashCode();
	}

}