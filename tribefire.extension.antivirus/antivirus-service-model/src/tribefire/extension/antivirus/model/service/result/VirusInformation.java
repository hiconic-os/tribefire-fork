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

import java.util.List;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Unmodifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * 
 *
 */
public interface VirusInformation extends AntivirusResult {

	EntityType<? extends VirusInformation> T = EntityTypes.T(VirusInformation.class);

	String details = "details";
	String numberInfectedResources = "numberInfectedResources";
	String durationInMs = "durationInMs";

	@Name("Details")
	@Description("Detailed information about scan runs")
	@Unmodifiable
	List<AbstractAntivirusResult> getDetails();
	void setDetails(List<AbstractAntivirusResult> details);

	@Name("Number Of Infected Resources")
	@Unmodifiable
	long getNumberInfectedResources();
	void setNumberInfectedResources(long numberInfectedResources);

	@Name("Duration")
	@Description("Total duration in ms")
	@Unmodifiable
	long getDurationInMs();
	void setDurationInMs(long durationInMs);
}
