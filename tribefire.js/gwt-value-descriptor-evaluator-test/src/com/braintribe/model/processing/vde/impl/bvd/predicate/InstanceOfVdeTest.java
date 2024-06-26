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
package com.braintribe.model.processing.vde.impl.bvd.predicate;

import org.junit.Test;

import com.braintribe.model.bvd.predicate.InstanceOf;
import com.braintribe.model.processing.vde.impl.misc.Child;
import com.braintribe.model.processing.vde.impl.misc.Person;

public class InstanceOfVdeTest extends AbstractPredicateVdeTest {

	@Test
	public void testStringStrinInstanceOf() throws Exception {

		InstanceOf predicate = $.instanceOf();
		Person value1 = Person.T.create();
		String value2 = Person.class.getName();

		predicate.setLeftOperand(value1);
		predicate.setRightOperand(value2);

		Object result = evaluate(predicate);
		validatePositiveResult(result);

	}

	@Test
	public void testStringIntegerInstanceOf() throws Exception {

		InstanceOf predicate = $.instanceOf();
		String value1 = "hello";
		Integer value2 = new Integer(1);

		predicate.setLeftOperand(value1);
		predicate.setRightOperand(value2);

		Object result = evaluate(predicate);
		validateNegativeResult(result);

	}

	@Test
	public void testChildPersonInstanceOf() throws Exception {

		InstanceOf predicate = $.instanceOf();
		Child value1 = Child.T.create();
		Person value2 = Person.T.create();

		predicate.setLeftOperand(value1);
		predicate.setRightOperand(value2);

		Object result = evaluate(predicate);
		validateNegativeResult(result);

	}

	@Test
	public void testChildChildInstanceOf() throws Exception {

		InstanceOf predicate = $.instanceOf();
		Child value1 = Child.T.create();
		Child value2 = Child.T.create();

		predicate.setLeftOperand(value1);
		predicate.setRightOperand(value2);

		Object result = evaluate(predicate);
		validatePositiveResult(result);

	}

}
