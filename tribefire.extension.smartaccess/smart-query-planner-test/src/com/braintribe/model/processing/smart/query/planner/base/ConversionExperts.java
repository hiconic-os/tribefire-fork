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
package com.braintribe.model.processing.smart.query.planner.base;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.accessdeployment.smart.meta.conversion.DateToString;
import com.braintribe.model.accessdeployment.smart.meta.conversion.LongToString;
import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.smartquery.eval.api.SmartConversionExpert;
import com.braintribe.model.query.smart.processing.eval.context.conversion.DateToStringExpert;
import com.braintribe.model.query.smart.processing.eval.context.conversion.LongToStringExpert;

/**
 * 
 */
public class ConversionExperts {

	public static final Map<EntityType<? extends SmartConversion>, SmartConversionExpert<?>> DEFAULT_CONVERSION_EXPERTS = newMap();

	static {
		DEFAULT_CONVERSION_EXPERTS.put(DateToString.T, DateToStringExpert.INSTANCE);
		DEFAULT_CONVERSION_EXPERTS.put(LongToString.T, LongToStringExpert.INSTANCE);
	}

}
