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
package com.braintribe.model.processing.mpc.evaluator.impl.logic;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.braintribe.model.generic.path.api.IModelPathElement;
import com.braintribe.model.generic.path.api.ModelPathElementType;
import com.braintribe.model.mpc.logic.MpcConjunction;
import com.braintribe.model.mpc.logic.MpcJunctionCapture;
import com.braintribe.model.path.GmModelPathElementType;
import com.braintribe.model.processing.MpGenerator;
import com.braintribe.model.processing.mpc.evaluator.api.MpcMatch;

/**
 * Provides tests for {@link MpcConjunctionEvaluator}.
 * 
 */
public class MpcConjunctionEvaluatorTest extends MpcJunctionEvaluatorTest {

	/**
	 * Validate that a {@link MpcConjunction} condition of two operands will MATCH against a
	 * {@link IModelPathElement} which consists of "Root.favouriteColours[1]" ( {@link MpGenerator#getTernaryPath()}).
	 */
	@Test
	public void testMatchingMultipleOperands() throws Exception {

		IModelPathElement path = MpGenerator.getTernaryPath();
		MpcConjunction condition = $.conjunction(MpcJunctionCapture.first, 
															$.elementTypeCondition(GmModelPathElementType.ListItem),
															$.negation($.elementTypeCondition(GmModelPathElementType.Property)));

		int junctionCaptureLength = MpcJunctionCapture.values().length;

		for (int i = 0; i < junctionCaptureLength; i++) {
			MpcJunctionCapture currentJC = getJunctionCapture(i);
			condition.setJunctionCapture(currentJC);

			MpcMatch evaluationResult = multipleOperandSuccessTest(condition, path);

			//validate output
			assertThat(evaluationResult).isNotNull();
			switch (currentJC) {
				case first:
				case shortest:
					assertThat(evaluationResult.getPath().getDepth()).isEqualTo(1);
					break;
				case longest:
				case last:
				case none:
					assertThat(evaluationResult.getPath().getDepth()).isEqualTo(2);
					break;
				// unknown junction capture
				default:
					fail("Unsupported Junction Capture: " + currentJC);
			}
		}
	}

	/**
	 * Validate that a {@link MpcConjunction} condition of multiple operands will NOT MATCH against a
	 * {@link IModelPathElement} which consists of "Root.name" ( {@link MpGenerator#getSimplePath()}).
	 */
	@Test
	public void testNotMatchingMultipleOperands() throws Exception {

		IModelPathElement path = MpGenerator.getSimplePath();
		MpcConjunction condition = $.conjunction(MpcJunctionCapture.first,
															$.elementTypeCondition(GmModelPathElementType.Property),
															$.negation($.elementTypeCondition(GmModelPathElementType.Property)));

		int junctionCaptureLength = MpcJunctionCapture.values().length;

		for (int i = 0; i < junctionCaptureLength; i++) {
			MpcJunctionCapture currentJC = getJunctionCapture(i);
			condition.setJunctionCapture(currentJC);

			MpcMatch evaluationResult = multipleOperandFailureTest(condition, path);

			// validate output
			switch (currentJC) {
				case shortest:
				case longest:
				case last:
				case first:
				case none:
					assertThat(evaluationResult).isNull();
					break;
				// unknown junction capture
				default:
					fail("Unsupported Junction Capture: " + currentJC);
			}
		}
	}


	/**
	 * Validate that a {@link MpcConjunction} condition of single operand will NOT MATCH against a
	 * {@link IModelPathElement} which consists of "Root.favouriteColours[1]" ( {@link MpGenerator#getTernaryPath()}).
	 */	
	@Test
	public void testNotMatchingSingleOperand() throws Exception {

		MpcConjunction condition = $.singleOperandConjunction();

		IModelPathElement path = MpGenerator.getTernaryPath();

		int junctionCaptureLength = MpcJunctionCapture.values().length;

		for (int i = 0; i < junctionCaptureLength; i++) {
			MpcJunctionCapture currentJC = getJunctionCapture(i);
			condition.setJunctionCapture(currentJC);

			MpcMatch evaluationResult = singleOperandFailureTest(condition, path);

			switch (currentJC) {
				case shortest:
				case longest:
				case last:
				case first:
				case none:
					// validate that the result is one item shorter
					assertThat(evaluationResult).isNull();

					break;
				// unknown junction capture
				default:
					fail("Unsupported Junction Capture: " + currentJC);
			}
		}
	}

	/**
	 * Validate that a {@link MpcConjunction} condition of single operand will  MATCH against a
	 * {@link IModelPathElement} which consists of "Root.name" ( {@link MpGenerator#getSimplePath()}).
	 */	
	@Test
	public void testMatchingSingleOperand() throws Exception {

		MpcConjunction condition = $.singleOperandConjunction();

		IModelPathElement path = MpGenerator.getSimplePath();

		int junctionCaptureLength = MpcJunctionCapture.values().length;

		for (int i = 0; i < junctionCaptureLength; i++) {
			MpcJunctionCapture currentJC = getJunctionCapture(i);
			condition.setJunctionCapture(currentJC);

			MpcMatch evaluationResult = singleOperandSuccessTest(condition, path);

			switch (currentJC) {
				case shortest:
				case longest:
				case last:
				case first:

					assertThat(evaluationResult).isNotNull();
					// validate that the result is one item shorter
					assertThat(evaluationResult.getPath().getDepth()).isEqualTo(0);
					assertEquals(evaluationResult.getPath().getDepth(), 0);
					// validate that the current element is indeed the root
					assertThat(evaluationResult.getPath().getElementType()).isEqualTo(
							ModelPathElementType.Root);
					break;

				case none:

					// validate that the result is same size
					assertEquals(evaluationResult.getPath().getDepth(), 1);
					// validate that the current element is indeed the property
					assertEquals(evaluationResult.getPath().getElementType(), ModelPathElementType.Property);
					// validate that the previous element is indeed the root
					assertEquals(evaluationResult.getPath().getPrevious().getElementType(), ModelPathElementType.Root);

					break;
				// unknown junction capture
				default:
					fail("Unsupported Junction Capture: " + currentJC);
			}
		}
	}
}
