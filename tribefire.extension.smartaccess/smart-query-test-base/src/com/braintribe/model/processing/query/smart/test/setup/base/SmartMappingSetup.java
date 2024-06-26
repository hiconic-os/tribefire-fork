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
package com.braintribe.model.processing.query.smart.test.setup.base;

import com.braintribe.model.accessdeployment.smart.SmartAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.query.smart.test.model.deployment.MoodAccess;

/**
 * @author peter.gazdik
 */
public class SmartMappingSetup {

	public static final String DATE_PATTERN = "ddMMyyyy HHmmss";

	public static final String accessIdA = "accessA";
	public static final String accessIdB = "accessB";
	public static final String accessIdS = "accessS";

	public static final String modelAName = "com.braintribe.model:SmartTestModel_A#2.0";
	public static final String modelBName = "com.braintribe.model:SmartTestModel_B#2.0";
	public static final String modelSName = "com.braintribe.model:SmartTestModel_S#2.0";

	public GmMetaModel modelA;
	public GmMetaModel modelB;
	public GmMetaModel modelS;

	public MoodAccess accessA;
	public MoodAccess accessB;
	public SmartAccess accessS;

}
