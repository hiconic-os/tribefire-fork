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

import com.braintribe.model.processing.query.smart.test.builder.repo.RepositoryDriver;
import com.braintribe.model.processing.query.smart.test.model.accessA.CompositeIkpaEntityA;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;

/**
 * 
 */
public class CompositeIkpaEntityABuilder extends AbstractBuilder<CompositeIkpaEntityA, CompositeIkpaEntityABuilder> {

	public static CompositeIkpaEntityABuilder newInstance(SmartDataBuilder dataBuilder) {
		return new CompositeIkpaEntityABuilder(dataBuilder.repoDriver());
	}

	public CompositeIkpaEntityABuilder(RepositoryDriver repoDriver) {
		super(CompositeIkpaEntityA.class, repoDriver);
	}

	public CompositeIkpaEntityABuilder personData(PersonA personA) {
		instance.setPersonId(personA.getId());
		instance.setPersonName(personA.getNameA());

		return this;
	}

	public CompositeIkpaEntityABuilder personData_Set(PersonA personA) {
		instance.setPersonId_Set(personA.getId());
		instance.setPersonName_Set(personA.getNameA());

		return this;
	}

	public CompositeIkpaEntityABuilder personId(Long value) {
		instance.setPersonId(value);
		return this;
	}

	public CompositeIkpaEntityABuilder personName(String value) {
		instance.setPersonName(value);
		return this;
	}

	public CompositeIkpaEntityABuilder description(String value) {
		instance.setDescription(value);
		return this;
	}

}
