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
package com.braintribe.gm.yaml.config.test;

import java.io.File;
import java.io.StringReader;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.braintribe.gm.config.yaml.ModeledYamlConfiguration;
import com.braintribe.gm.config.yaml.YamlConfigurations;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.config.ConfigurationError;
import com.braintribe.gm.model.reason.essential.ParseError;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.testing.model.test.technical.features.SimpleEntity;

public class ModeledYamlConfigTest {
	@Test
	public void testAbsenceInformation() {
		String yaml = "{}";
		
		Maybe<SimpleEntity> maybe = YamlConfigurations.read(SimpleEntity.T) //
			.absentifyMissingProperties() //
			.from(new StringReader(yaml));
		
		SimpleEntity simpleEntity = maybe.get();
		
		Property property = SimpleEntity.T.getProperty("stringProperty");
		
		Assertions.assertThat(property.isAbsent(simpleEntity)).isTrue();
	}
	
	@Test
	public void test1stOrderParseError() {
		
		Maybe<SimpleEntity> maybe = YamlConfigurations.read(SimpleEntity.T) //
				.from(new File("res/syntax-error.yaml"));

		Assertions.assertThat(maybe.isUnsatisfiedBy(ConfigurationError.T)).isTrue();
		
		System.out.println(maybe.whyUnsatisfied().stringify());
		
	}
	
	@Test
	public void test2ndOrderParseError() {
		Maybe<SimpleEntity> maybe = YamlConfigurations.read(SimpleEntity.T) //
				.from(new File("res/property-error.yaml"));

		Assertions.assertThat(maybe.isUnsatisfiedBy(ConfigurationError.T)).isTrue();
		
		System.out.println(maybe.whyUnsatisfied().stringify());
			
	}

	@Test
	public void testPropertyInjection() {
		ModeledYamlConfiguration config = new ModeledYamlConfiguration();
		config.setConfigFolder(new File("res/conf"));;
		SimpleEntity simpleEntity = config.configReasoned(SimpleEntity.T).get();
		Assertions.assertThat(simpleEntity.getStringProperty()).isEqualTo("first-second");
	}
}