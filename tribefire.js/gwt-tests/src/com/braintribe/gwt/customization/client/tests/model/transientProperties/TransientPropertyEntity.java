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
package com.braintribe.gwt.customization.client.tests.model.transientProperties;

import java.sql.Timestamp;

import com.braintribe.model.generic.annotation.Transient;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface TransientPropertyEntity extends TransientPropertySuper1, TransientPropertySuper2 {

	EntityType<TransientPropertyEntity> T = EntityTypes.T(TransientPropertyEntity.class);

	int NUMBER_OF_PROPS = TransientPropertySuper1.NUMBER_OF_PROPS + TransientPropertySuper2.NUMBER_OF_PROPS + 4;

	@Transient
	String getName();
	void setName(String name);

	@Transient
	Object getObject();
	void setObject(Object object);

	@Transient
	Timestamp getTimestamp();
	void setTimestamp(Timestamp timestamp);

	@Transient
	TransientPropertyEntity getSelf();
	void setSelf(TransientPropertyEntity self);

}
