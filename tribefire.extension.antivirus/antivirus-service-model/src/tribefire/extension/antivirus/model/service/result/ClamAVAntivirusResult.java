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
package tribefire.extension.antivirus.model.service.result;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Unmodifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * 
 * ClamAV Provider result
 * 
 *
 */
public interface ClamAVAntivirusResult extends AbstractAntivirusResult {

	EntityType<? extends ClamAVAntivirusResult> T = EntityTypes.T(ClamAVAntivirusResult.class);

	@Name("Status")
	@Description("Status of the antivirus scanning - PASSED, FAILED, ERROR")
	@Unmodifiable
	String getStatus();
	void setStatus(String status);
	
	@Name("Signature")
	@Description("Signature of the virus")
	@Unmodifiable
	String getSignature();
	void setSignature(String signature);
	
	@Name("Exception")
	@Description("Exception in case of error during the scanning")
	@Unmodifiable
	String getException();
	void setException(String exception);
	
	@Override
	default String getDetails() {
		switch (Status.valueOf(this.getStatus())) {
			case PASSED: 
				return String.format("No virus found in content.");
			case FAILED: 
				return String.format("Status: %s, Signature of the virus: %s", getStatus(), getSignature());
			case ERROR: 
				return String.format("Status: %s, Received error: %s", getStatus(), getException());
			default:
				throw new IllegalArgumentException("Invalid status received: " + getStatus());	
		}				
	}
	
	enum Status {PASSED, FAILED, ERROR}
}
