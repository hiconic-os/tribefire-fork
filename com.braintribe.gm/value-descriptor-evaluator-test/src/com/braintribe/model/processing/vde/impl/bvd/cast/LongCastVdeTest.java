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
package com.braintribe.model.processing.vde.impl.bvd.cast;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.model.bvd.cast.LongCast;
import com.braintribe.model.processing.vde.evaluator.api.VdeRuntimeException;
import com.braintribe.model.processing.vde.evaluator.impl.bvd.cast.LongCastVde;
import com.braintribe.model.processing.vde.impl.VDGenerator;
import com.braintribe.model.processing.vde.test.VdeTest;

/**
 * Provides tests for {@link LongCastVde}.
 * 
 */
public class LongCastVdeTest extends VdeTest {

	public static VDGenerator $ = new VDGenerator(); 
	
	@Test
	public void testNumberToLongast() throws Exception {
		
		Object [] numbers = CastUtil.getAllPossibleNumberTypesArray();
		LongCast cast = $.longCast();
		for(Object number: numbers){
			
			cast.setOperand(number);
			Object result = evaluate(cast);

			assertThat(result).isNotNull();
			assertThat(result).isInstanceOf(Long.class);
		}		
	}

	@Test (expected=VdeRuntimeException.class)
	public void testBooleanToLongCastFail() throws Exception {
		Boolean x = new Boolean(true);

		LongCast cast = $.longCast();
		cast.setOperand(x);

		evaluate(cast);
	}
}
