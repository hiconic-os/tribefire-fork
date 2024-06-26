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
package tribefire.extension.metrics.model.deployment.service.aspect;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Distribution Summary Aspect
 * 
 *
 */
public interface MetricsSummaryAspect extends MetricsAspect {

	final EntityType<MetricsSummaryAspect> T = EntityTypes.T(MetricsSummaryAspect.class);

	String baseUnit = "baseUnit";
	String scale = "scale";
	String summaryStatistics = "summaryStatistics";

	String getBaseUnit();
	void setBaseUnit(String baseUnit);

	Double getScale();
	void setScale(Double scale);

	@Mandatory
	@Initializer("enum(tribefire.extension.metrics.model.deployment.service.aspect.SummaryStatistics,REQUEST_SIZE)")
	SummaryStatistics getSummaryStatistics();
	void setSummaryStatistics(SummaryStatistics summaryStatistics);

}
