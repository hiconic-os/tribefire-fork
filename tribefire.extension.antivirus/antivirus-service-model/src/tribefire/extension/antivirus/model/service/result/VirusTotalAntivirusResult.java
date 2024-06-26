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
 * VirusTotal Provider result
 * 
 *
 */
public interface VirusTotalAntivirusResult extends AbstractAntivirusResult {

	EntityType<? extends VirusTotalAntivirusResult> T = EntityTypes.T(VirusTotalAntivirusResult.class);

	@Name("Permalink")
	@Description("Permalink")
	@Unmodifiable
	String getPermalink();
	void setPermalink(String permalink);
	
	@Name("ScanDate")
	@Description("Scan date")
	@Unmodifiable
	String getScanDate();
	void setScanDate(String scanDate);
	
	@Name("ResourceID")
	@Description("The id of the scanned resource")
	@Unmodifiable
	String getResourceID();
	void setResourceID(String resourceID);
	
	@Override
	default String getDetails() {
		return String.format("Permalink: %s, Scan date: %s, Resource id: %s", getPermalink(), getScanDate(), getResourceID());						
	}
}
