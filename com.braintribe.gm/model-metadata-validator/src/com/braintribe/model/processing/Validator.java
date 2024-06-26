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
package com.braintribe.model.processing;

import java.util.List;

import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.generic.typecondition.basic.IsAssignableTo;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.impl.ValidatorImpl;
import com.braintribe.model.processing.meta.cmd.CmdResolverImpl;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.validation.expert.TypeExpert;

/**
 * Validates an entity and its properties transitively. What exactly is
 * validated depends on the implementations of the experts: {@link ValidationExpert}s and
 * {@link PropertyValidationExpert}s which can be passed via an {@link ValidationExpertRegistry}.
 */
public interface Validator {
	/**
	 * Creates a simple Validator using the {@link ValidationExpertRegistry#createDefault() default
	 * ValidationExpertRegistry}. It also adds another expert that checks if the object to be validated is assignable to
	 * a certain type.
	 * 
	 * @param rootType
	 *            The validated object will be validated against being assignable to this type.
	 * @param model
	 *            will be used to determine the metadata of the validated object
	 */
	static Validator create(GenericModelType rootType, GmMetaModel model) {
		IsAssignableTo isType = IsAssignableTo.T.create();
		isType.setTypeSignature(rootType.getTypeSignature());
		return create(isType, model);
	}

	/**
	 * Creates a simple Validator using the {@link ValidationExpertRegistry#createDefault() default
	 * ValidationExpertRegistry}. It also adds another expert that checks if the object to be validated meets a certain
	 * type condotion.
	 * 
	 * @param typeCondition
	 *            The validated object will be validated against meeting this type condition.
	 * @param model
	 *            will be used to determine the metadata of the validated object
	 */
	static Validator create(TypeCondition typeCondition, GmMetaModel model) {
		CmdResolverImpl cmdResolver = new CmdResolverImpl(new BasicModelOracle(model));
		ValidationExpertRegistry validationExpertRegistry = ValidationExpertRegistry.createDefault();
		Validator validator = new ValidatorImpl(cmdResolver, validationExpertRegistry);

		validationExpertRegistry.addRootExpert(new TypeExpert(typeCondition));
		return validator;
	}

	/**
	 * Traverses passed value and runs validations. As opposed to {@link #validate(Object)} this method does not throw
	 * an exception if any validation failed but it returns a List of modeled {@link ConstraintViolation}s. The
	 * traversing is always finished so you will get all violated constraints as a result.
	 * 
	 * @param rootValue
	 *            Value to be validated
	 */
	List<ConstraintViolation> checkConstraints(Object rootValue);

	/**
	 * Traverses passed value and runs validations. As opposed to {@link #checkConstraints(Object)} this method throws
	 * an {@link IllegalArgumentException} with a detailed human-readable message listing all violated constraints if any was reported by a
	 * ValidationExpert.
	 * 
	 * @param rootValue
	 *            Value to be validated
	 * @throws IllegalArgumentException if any constraint violation was detected           
	 */
	void validate(Object rootValue);

}