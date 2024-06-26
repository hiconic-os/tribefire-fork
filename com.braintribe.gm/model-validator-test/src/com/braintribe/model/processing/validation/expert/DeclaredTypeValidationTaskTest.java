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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.processing.validation.ValidationContext;
import com.braintribe.model.processing.validation.ValidationMode;
import com.braintribe.model.processing.validation.ValidationType;

public class DeclaredTypeValidationTaskTest extends AbstractValidationTaskTest {
	
	private GmMetaModel declaringModel;
	private ValidationContext context;
	
	@Before
	public void prepare() {
		declaringModel = TestMetaModel.T.create();
		
		context = new ValidationContext(ValidationType.SKELETON, ValidationMode.DECLARATION);
	}
	
	@Test
	public void testExecuting_EntityType() {
		GmType type = TestEntityType.T.create();
		type.setDeclaringModel(declaringModel);
		ValidationTask task = new DeclaredTypeValidationTask(declaringModel, type);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(0);
		assertThat(context.getValidationTasks()).hasSize(2);
		assertThat(context.pollValidationTask()).isInstanceOf(DeclaredEntityTypeValidationTask.class);
		assertThat(context.pollValidationTask()).isInstanceOf(TypeValidationTask.class);
	}
	
	@Test
	public void testExecuting_EnumType() {
		GmType type = TestEnumType.T.create();
		type.setDeclaringModel(declaringModel);
		ValidationTask task = new DeclaredTypeValidationTask(declaringModel, type);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(0);
		assertThat(context.getValidationTasks()).hasSize(2);
		assertThat(context.pollValidationTask()).isInstanceOf(DeclaredEnumTypeValidationTask.class);
		assertThat(context.pollValidationTask()).isInstanceOf(TypeValidationTask.class);
	}
	
	@Test
	public void testExecuting_WrongDeclaringModel() {
		GmType type = TestEntityType.T.create();
		ValidationTask task = new DeclaredTypeValidationTask(declaringModel, type);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_NotACustomType() {
		GmType type = TestCollectionType.T.create();
		type.setDeclaringModel(declaringModel);
		ValidationTask task = new DeclaredTypeValidationTask(declaringModel, type);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
}
