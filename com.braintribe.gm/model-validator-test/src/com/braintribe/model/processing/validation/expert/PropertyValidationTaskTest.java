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

import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.validation.ValidationContext;
import com.braintribe.model.processing.validation.ValidationMode;
import com.braintribe.model.processing.validation.ValidationType;

public class PropertyValidationTaskTest extends AbstractValidationTaskTest {
	
	private GmProperty property;
	private ValidationContext context;
	
	@Before
	public void prepare() {
		property = TestProperty.T.create();
		property.setName("test-name");
		property.setDeclaringType(TestEntityType.T.create());
		property.setType(TestEntityType.T.create());
		
		context = new ValidationContext(ValidationType.SKELETON, ValidationMode.DECLARATION);
	}
	
	@Test
	public void testExecuting() {
		ValidationTask task = new PropertyValidationTask(property);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(0);
		assertThat(context.getValidationTasks()).hasSize(0);
	}
	
	@Test
	public void testExecuting_MissingName() {
		ValidationTask task = new PropertyValidationTask(property);
		property.setName(null);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_InvalidName1() {
		ValidationTask task = new PropertyValidationTask(property);
		property.setName("Test-name");
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_InvalidName2() {
		ValidationTask task = new PropertyValidationTask(property);
		property.setName("tEst-name");
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_MissingDeclaringType() {
		ValidationTask task = new PropertyValidationTask(property);
		property.setDeclaringType(null);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
	
	@Test
	public void testExecuting_MissingType() {
		ValidationTask task = new PropertyValidationTask(property);
		property.setType(null);
		
		task.execute(context);
		
		assertThat(extractErrorMessages(context)).hasSize(1);
	}
}
