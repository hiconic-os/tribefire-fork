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
package tribefire.extension.antivirus.model.deployment.repository.configuration;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Confidential;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface VirusTotalSpecification extends ProviderSpecification {

	EntityType<VirusTotalSpecification> T = EntityTypes.T(VirusTotalSpecification.class);

	String apiKey = "apiKey";
	String pollIntervalInSec = "pollIntervalInSec";
	String pollDurationInSec = "pollDurationInSec";

	@Mandatory
	@Name("API key")
	@Confidential
	String getApiKey();
	void setApiKey(String apiKey);

	@Mandatory
	@Name("Poll Interval")
	@Description("Poll interval in seconds")
	@Initializer("2")
	int getPollIntervalInSec();
	void setPollIntervalInSec(int pollIntervalInSec);

	@Mandatory
	@Name("Poll Duration")
	@Description("Max Poll duration in seconds")
	@Initializer("60")
	int getPollDurationInSec();
	void setPollDurationInSec(int pollDurationInSec);
}
