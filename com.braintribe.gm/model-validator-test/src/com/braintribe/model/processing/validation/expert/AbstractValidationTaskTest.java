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
package com.braintribe.model.processing.validation.expert;

import java.util.List;
import java.util.stream.Collectors;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmCollectionType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.override.GmCustomTypeOverride;
import com.braintribe.model.meta.override.GmPropertyOverride;
import com.braintribe.model.meta.selector.MetaDataSelector;
import com.braintribe.model.processing.validation.ValidationContext;
import com.braintribe.model.processing.validation.ValidationMessage;
import com.braintribe.model.processing.validation.ValidationMessageLevel;

public abstract class AbstractValidationTaskTest {

	protected List<ValidationMessage> extractErrorMessages(ValidationContext context) {
		return context.getValidationMessages().stream().filter(vm -> vm.getLevel().equals(ValidationMessageLevel.ERROR))
				.collect(Collectors.toList());
	}
	
	public interface TestMetaModel extends GmMetaModel {
		EntityType<TestMetaModel> T = EntityTypes.T(TestMetaModel.class);
	}

	public interface TestType extends GmType {
		EntityType<TestType> T = EntityTypes.T(TestType.class);
	}

	public interface TestTypeOverride extends GmCustomTypeOverride {
		EntityType<TestTypeOverride> T = EntityTypes.T(TestTypeOverride.class);
	}

	public interface TestEntityType extends GmEntityType {
		EntityType<TestEntityType> T = EntityTypes.T(TestEntityType.class);
	}

	public interface TestEnumType extends GmEnumType {
		EntityType<TestEnumType> T = EntityTypes.T(TestEnumType.class);
	}
	
	public interface TestCollectionType extends GmCollectionType {
		EntityType<TestCollectionType> T = EntityTypes.T(TestCollectionType.class);
	}

	public interface TestEnumConstant extends GmEnumConstant {
		EntityType<TestEnumConstant> T = EntityTypes.T(TestEnumConstant.class);
	}

	public interface TestProperty extends GmProperty {
		EntityType<TestProperty> T = EntityTypes.T(TestProperty.class);
	}
	
	public interface TestPropertyOverride extends GmPropertyOverride {
		EntityType<TestPropertyOverride> T = EntityTypes.T(TestPropertyOverride.class);
	}

	public interface TestMetaData extends MetaData {
		EntityType<TestMetaData> T = EntityTypes.T(TestMetaData.class);
	}

	public interface TestMetaDataSelector extends MetaDataSelector {
		EntityType<TestMetaDataSelector> T = EntityTypes.T(TestMetaDataSelector.class);
	}
}
