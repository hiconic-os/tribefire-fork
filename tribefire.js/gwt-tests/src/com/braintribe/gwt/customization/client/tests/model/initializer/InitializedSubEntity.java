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
package com.braintribe.gwt.customization.client.tests.model.initializer;

import java.util.Date;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface InitializedSubEntity extends InitializedEntity {

	EntityType<InitializedSubEntity> T = EntityTypes.T(InitializedSubEntity.class);

	// override value with explicit one
	@Override
	@Initializer("88")
	int getIntValue();
	@Override
	void setIntValue(int intValue);

	// override with default (0)
	@Override
	@Initializer("0L")
	long getLongValue();
	@Override
	void setLongValue(long longValue);

	// re-declared does not change the default
	@Override
	boolean getBooleanValue();
	@Override
	void setBooleanValue(boolean doubleValue);

	@Override
	@Initializer("null")
	Date getDateValue();
	@Override
	void setDateValue(Date value);

	long getNewLongValue();
	void setNewLongValue(long value);

}
