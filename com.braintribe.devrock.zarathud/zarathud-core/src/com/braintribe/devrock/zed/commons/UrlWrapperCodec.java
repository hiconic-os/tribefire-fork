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
package com.braintribe.devrock.zed.commons;

import java.net.URL;

import com.braintribe.cc.lcd.CodingMap;
import com.braintribe.cc.lcd.CodingSet;
import com.braintribe.cc.lcd.HashSupportWrapperCodec;
import com.braintribe.model.artifact.essential.ArtifactIdentification;

/**
 * wrapper codec for the {@link CodingSet} or {@link CodingMap} based structures with {@link ArtifactIdentification} 
 * @author Pit
 *
 */
public class UrlWrapperCodec extends HashSupportWrapperCodec<URL> {
	
	public UrlWrapperCodec() {
		super(true);
	}

	@Override
	protected int entityHashCode(URL e) {			    
		return e.hashCode();
	}

	@Override
	protected boolean entityEquals(URL e1, URL e2) {
		
		if (
				(e1 == null && e2 != null) ||
				(e1 != null && e2 == null)
			)
		return false;
		
		return e1.sameFile(e2);
	
	}

}
