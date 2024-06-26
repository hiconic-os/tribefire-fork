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
package com.braintribe.model.processing.mpc.evaluator.impl.structure;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.model.generic.path.api.IModelPathElement;
import com.braintribe.model.generic.path.api.ModelPathElementType;
import com.braintribe.model.mpc.structure.MpcElementType;
import com.braintribe.model.path.GmModelPathElementType;
import com.braintribe.model.processing.MpGenerator;
import com.braintribe.model.processing.mpc.MPC;
import com.braintribe.model.processing.mpc.evaluator.api.MpcMatch;
import com.braintribe.model.processing.mpc.evaluator.impl.AbstractMpcTest;

/**
 * Provides tests for {@link MpcElementTypeEvaluator}.
 * 
 */
public class MpcElementTypeEvaluatorTest extends AbstractMpcTest {

	/**
	 * Validate that a {@link MpcElementType} condition where elementType will loop through all
	 * {@link GmModelPathElementType} MATCH against a {@link IModelPathElement} which consists of "Root.name" (
	 * {@link MpGenerator#getSimplePath()}), according to the type of the operator (Property).
	 */
	@Test
	public void testPropertyElementTypeMatch() throws Exception {

		IModelPathElement path = MpGenerator.getSimplePath();

		int gmModelPathElementLength = GmModelPathElementType.values().length;

		for (int i = 0; i < gmModelPathElementLength; i++) {
			GmModelPathElementType currentGMType = getGmModelPathElementType(i);
			MpcElementType condition = $.elementTypeCondition(currentGMType);

			// Entry point is not supported
			if (condition.getElementType() == GmModelPathElementType.EntryPoint)
				continue;

			// evaluate the condition against the path
			MpcMatch evalResult = MPC.mpcMatches(condition, path);

			// validate output
			switch (condition.getElementType()) {
				case EntryPoint:
				case Root:
				case SetItem:
				case ListItem:
				case MapKey:
				case MapValue:
					assertThat(evalResult).isNull();
					break;
				case Property:
					assertThat(evalResult).isNotNull();
					// validate that the result is one item shorter
					assertThat(evalResult.getPath().getDepth()).isEqualTo(0);
					// validate that the current element is indeed the root
					assertThat(evalResult.getPath().getElementType()).isEqualTo(ModelPathElementType.Root);
					break;

				default:
					break;

			}
		}
	}
	
	/**
	 * Validate that a {@link MpcElementType} condition where elementType will loop through all
	 * {@link GmModelPathElementType} MATCH against a {@link IModelPathElement} which consists of "Root.favouriteColours[1]" (
	 * {@link MpGenerator#getTernaryPath()}), according to the type of the operator (ListItem).
	 */
	@Test
	public void testListElementTypeMatch() throws Exception {

		IModelPathElement path = MpGenerator.getTernaryPath();

		int gmModelPathElementLength = GmModelPathElementType.values().length;

		for (int i = 0; i < gmModelPathElementLength; i++) {
			GmModelPathElementType currentGMType = getGmModelPathElementType(i);
			MpcElementType condition = $.elementTypeCondition(currentGMType);

			// Entry point is not supported
			if (condition.getElementType() == GmModelPathElementType.EntryPoint)
				continue;

			// evaluate the condition against the path
			MpcMatch evalResult = MPC.mpcMatches(condition, path);

			// validate output
			switch (condition.getElementType()) {
				case EntryPoint:
				case Root:
				case SetItem:
				case Property:
				case MapKey:
				case MapValue:
					assertThat(evalResult).isNull();
					break;
				case ListItem:
					assertThat(evalResult).isNotNull();
					// validate that the result is one item shorter
					assertThat(evalResult.getPath().getDepth()).isEqualTo(1);
					// validate that the current element is indeed the root
					assertThat(evalResult.getPath().getElementType()).isEqualTo(ModelPathElementType.Property);
					break;

				default:
					break;

			}
		}
	}

	/**
	 * @param position
	 *            index in array
	 * @return The {@link GmModelPathElementType} at the indicated position
	 */
	private static GmModelPathElementType getGmModelPathElementType(int position) {
		return GmModelPathElementType.values()[position];
	}
}
