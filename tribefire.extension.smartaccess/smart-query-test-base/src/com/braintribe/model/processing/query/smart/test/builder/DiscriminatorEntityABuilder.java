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
import com.braintribe.model.processing.query.smart.test.model.accessA.discriminator.DiscriminatorEntityA;

/**
 * 
 */
public class DiscriminatorEntityABuilder extends AbstractBuilder<DiscriminatorEntityA, DiscriminatorEntityABuilder> {

	public static DiscriminatorEntityABuilder newInstance(SmartDataBuilder dataBuilder) {
		return new DiscriminatorEntityABuilder(dataBuilder.repoDriver());
	}

	public DiscriminatorEntityABuilder(RepositoryDriver repoDriver) {
		super(DiscriminatorEntityA.class, repoDriver);
	}

	public DiscriminatorEntityABuilder name(String value) {
		instance.setName(value);
		return this;
	}

	public DiscriminatorEntityABuilder type1Name(String value) {
		instance.setType1Name(value);
		return this;
	}

	public DiscriminatorEntityABuilder type2Name(String value) {
		instance.setType2Name(value);
		return this;
	}

	public DiscriminatorEntityABuilder discriminator(String value) {
		instance.setDiscriminator(value);
		return this;
	}

	public DiscriminatorEntityABuilder discriminator2(String value) {
		instance.setDiscriminator2(value);
		return this;
	}

}
