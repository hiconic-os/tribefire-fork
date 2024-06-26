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
package com.braintribe.devrock.zed.forensics.fingerprint;

import com.braintribe.zarathud.model.forensics.FingerPrint;

/**
 * all tokens a {@link FingerPrint} uses
 * @author pit
 */
public interface HasFingerPrintTokens {
	String ISSUE = "issue";
	String PROPERTY = "property";
	String ENTITY = "entity";
	String METHOD = "method";
	String FIELD = "field";
	String PACKAGE = "package";
	String TYPE = "type";
	String ARTIFACT = "artifact";
	String GROUP = "group";
	
	// not for identification of the owner of the fingerprint,
	// but for precise drill down, i.e. in case of EXCESS/MISSING dependencies, the ArtifactIdentification of the dependency
	String ISSUE_KEY = "issue_key";
	
	String FINGER_PRINT_PART_KEY = "fps";
	String FINGER_PRINT_LOCAL_KEY = "fingerprint.yaml";
}
