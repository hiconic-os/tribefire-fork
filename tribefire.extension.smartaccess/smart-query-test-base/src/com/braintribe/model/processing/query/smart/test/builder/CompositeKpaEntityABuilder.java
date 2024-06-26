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
import com.braintribe.model.processing.query.smart.test.model.accessA.CompositeKpaEntityA;
import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;

/**
 * 
 */
public class CompositeKpaEntityABuilder extends AbstractBuilder<CompositeKpaEntityA, CompositeKpaEntityABuilder> {

	public static CompositeKpaEntityABuilder newInstance(SmartDataBuilder dataBuilder) {
		return new CompositeKpaEntityABuilder(dataBuilder.repoDriver());
	}

	public CompositeKpaEntityABuilder(RepositoryDriver repoDriver) {
		super(CompositeKpaEntityA.class, repoDriver);
	}

	public CompositeKpaEntityABuilder personData(PersonA personA) {
		instance.setPersonId(personA.getCompositeId());
		instance.setPersonName(personA.getCompositeName());
		instance.setPersonCompanyName(personA.getCompositeCompanyName());

		return this;
	}

	public CompositeKpaEntityABuilder personId(Long value) {
		instance.setPersonId(value);
		return this;
	}

	public CompositeKpaEntityABuilder personName(String value) {
		instance.setPersonName(value);
		return this;
	}

	public CompositeKpaEntityABuilder personCompanyName(String value) {
		instance.setPersonCompanyName(value);
		return this;
	}

	public CompositeKpaEntityABuilder description(String value) {
		instance.setDescription(value);
		return this;
	}

}
