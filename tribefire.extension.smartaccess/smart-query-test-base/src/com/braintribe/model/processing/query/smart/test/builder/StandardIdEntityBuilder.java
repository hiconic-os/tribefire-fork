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
package com.braintribe.model.processing.query.smart.test.builder;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import com.braintribe.model.processing.query.smart.test.builder.repo.RepositoryDriver;
import com.braintribe.model.processing.query.smart.test.model.accessB.StandardIdEntity;

/**
 * 
 */
public class StandardIdEntityBuilder extends AbstractBuilder<StandardIdEntity, StandardIdEntityBuilder> {

	public static StandardIdEntityBuilder newInstance(SmartDataBuilder dataBuilder) {
		return new StandardIdEntityBuilder(dataBuilder.repoDriver());
	}

	public StandardIdEntityBuilder(RepositoryDriver repoDriver) {
		super(StandardIdEntity.class, repoDriver);
	}

	public StandardIdEntityBuilder name(String value) {
		instance.setName(value);
		return this;
	}

	public StandardIdEntityBuilder id(long value) {
		instance.setId(value);
		return this;
	}

	public StandardIdEntityBuilder parent(StandardIdEntity value) {
		instance.setParent(value);
		return this;
	}

	public StandardIdEntityBuilder children(StandardIdEntity... values) {
		instance.setChildren(asSet(values));
		return this;
	}

}
