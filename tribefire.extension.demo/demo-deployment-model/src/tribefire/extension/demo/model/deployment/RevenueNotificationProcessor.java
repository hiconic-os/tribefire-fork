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
package tribefire.extension.demo.model.deployment;

import java.math.BigDecimal;

import com.braintribe.model.extensiondeployment.StateChangeProcessor;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

// ADDED: Challenge 1
public interface RevenueNotificationProcessor extends StateChangeProcessor {
	
	EntityType<RevenueNotificationProcessor> T = EntityTypes.T(RevenueNotificationProcessor.class);
	
	/*
	 * Constants for each property name.
	 */
	String minRevenue = "minRevenue";
	String maxRevenue = "maxRevenue";
	
	
	@Initializer("1000000.00b")
	BigDecimal getMinRevenue();
	void setMinRevenue(BigDecimal averageRevenue);

	
	@Initializer("10000000.00b")
	BigDecimal getMaxRevenue();
	void setMaxRevenue(BigDecimal averageRevenue);

}
