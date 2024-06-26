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
package com.braintribe.gwt.customization.client.tests;

import com.braintribe.gwt.customization.client.tests.model.grindlebone.GbEntity;
import com.braintribe.model.generic.GmfException;

/**
 * @author peter.gazdik
 */
public class GrindleboneTest extends AbstractGwtTest {

	@Override
	protected void tryRun() throws GmfException {
		GbEntity gbEntity = GbEntity.T.create();
		gbEntity.setBooleanValue(true);
		gbEntity.setBooleanWrapper(true);
		gbEntity.setIntegerValue(23);
		gbEntity.setIntegerWrapper(23);
		gbEntity.setLongValue(4711L);
		gbEntity.setLongWrapper(4711L);
		gbEntity.setFloatValue((float)Math.PI);
		gbEntity.setFloatWrapper((float)Math.PI);
		gbEntity.setDoubleValue(Math.E);
		gbEntity.setDoubleWrapper(Math.E);
		gbEntity.setStringValue("laessig");

		Object objectValue = gbEntity.getStringValue();
		String stringValue = gbEntity.getStringValue();
		Class<?> class1 = objectValue.getClass();
		Class<?> class2 = stringValue.getClass();
		log(class1.toString());
		log(class2.toString());
		log(gbEntity.toString());
	}


}
