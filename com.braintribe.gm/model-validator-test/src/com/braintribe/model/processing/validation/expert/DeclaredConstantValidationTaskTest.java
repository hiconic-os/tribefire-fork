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

import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.processing.validation.ValidationContext;
import com.braintribe.model.processing.validation.ValidationMode;
import com.braintribe.model.processing.validation.ValidationType;

public class DeclaredConstantValidationTaskTest extends AbstractValidationTaskTest {
	
	private GmEnumType declaringType;
	private GmEnumConstant constant;
	private ValidationContext context;
	
	@Before
	public void prepare() {
		declaringType = TestEnumType.T.create();
		constant = TestEnumConstant.T.create();
		constant.setGlobalId("test-global-id");
		constant.setName("test-name");
		constant.setDeclaringType(declaringType);
		constant.setMetaData(asSet(TestMetaData.T.create(), TestMetaData.T.create()));
		
		context = new ValidationContext(ValidationType.SKELETON, ValidationMode.DECLARATION);
	}
	
	@Test
	public void testExecuting() {
		ValidationTask task = new DeclaredConstantValidationTask(declaringType, constant);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(0);
		assertThat(context.getValidationTasks()).hasSize(2);
		assertThat(context.pollValidationTask()).isInstanceOf(CoreMetaDataValidationTask.class);
		assertThat(context.pollValidationTask()).isInstanceOf(CoreMetaDataValidationTask.class);
	}
	
	@Test
	public void testExecuting_MissingGlobalId() {
		ValidationTask task = new DeclaredConstantValidationTask(declaringType, constant);
		constant.setGlobalId(null);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_WrongDeclaringType() {
		ValidationTask task = new DeclaredConstantValidationTask(declaringType, constant);
		constant.setDeclaringType(TestEnumType.T.create());
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_MissingName() {
		ValidationTask task = new DeclaredConstantValidationTask(declaringType, constant);
		constant.setName(null);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
}
