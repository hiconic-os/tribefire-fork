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
package com.braintribe.model.manipulation.parser.impl.manipulator;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.braintribe.model.processing.manipulation.parser.api.MutableGmmlManipulatorParserConfiguration;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;

/**
 * @author peter.gazdik
 */
@RunWith(Parameterized.class)
public class ErroneousManipulationTets extends AbstractManipulatorTest {

	private Class<? extends Exception> exceptionClass;
	private String exceptionMessage;
	private final boolean bufferEntireInput;

	@Parameters(name = "{0}")
	public static Object[][] data() {
		return new Object[][] { { "buffered", true }, { "unbuffered", false } };
	}

	public ErroneousManipulationTets(@SuppressWarnings("unused") String name, boolean bufferEntireInput) {
		this.bufferEntireInput = bufferEntireInput;
	}

	@Test
	public void brokenInput() {
		// @formatter:off
		applyWithExpectedError(
				"   [ $0 = (...)()" 
			);
		// @formatter:on

		assertError(IllegalStateException.class, "mismatched input '[' expecting <EOF> in line 1 at position 3. Source: \"[\"");
	}

	@Test
	public void invalidTokenInTheMiddleOfTheRule() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)()",
				".stringValue == 'Hallo'" 
			);
		// @formatter:on

		assertError(IllegalStateException.class, "no viable alternative at input '=' in line 2 at position 14. Source: \".stringValue ==\"");
	}

	@Test
	public void invalidTokenBetweenRules() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)()",
				".dateValue = date(1976Y, +0500Z",
				".booleanValue = true"
			);
		// @formatter:on

		assertError(IllegalStateException.class, "mismatched input '.' expecting ')' in line 3 at position 0. Source: \".\"");
	}

	@Test
	public void invalidDate() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)()",
				".dateValue = date(1976Y, +0500Q)"
			);
		// @formatter:on

		assertError(IllegalStateException.class,
				"no viable alternative at input '+0500' in line 2 at position 25. Source: \".dateValue = date(1976Y, +\"");
	}

	@Test
	public void invalidDate_ErrorOnNewLine() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)()",
				".dateValue =",
				"date(1976Y, +0500Q)"
			);
		// @formatter:on

		assertError(IllegalStateException.class, "no viable alternative at input '+0500' in line 3 at position 12. Source: \"date(1976Y, +\"");
	}

	@Test
	public void runtimeError_WrongVariableUsage() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = com.braintribe.model.resource.Resource", 
				"$0.name = 'Hallo'"
			);
		// @formatter:on

		assertErrorFuzzy(ClassCastException.class, "com.braintribe.model.resource.Resource-et cannot be cast to",
				"com.braintribe.model.generic.GenericEntity");
	}

	@Test
	public void runtimeError_InvalidStatement_SameLine() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)() +45"
			);
		// @formatter:on

		assertError(IllegalStateException.class,
				"mismatched input '+45' expecting <EOF> in line 1 at position 73. Source: \"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)() +\"");
	}

	@Test
	public void runtimeError_InvalidStatement_NewLine() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)()",
				"+45"
				);
		// @formatter:on

		assertError(IllegalStateException.class, "mismatched input '+45' expecting <EOF> in line 2 at position 0. Source: \"+\"");
	}

	@Test
	public void runtimeError_ProblematicEntity() {
		// @formatter:off
		applyWithExpectedError(
				"$0 = (Joat = com.braintribe.model.manipulation.parser.impl.model.Joat)('entity.problematic')"
				);
		// @formatter:on

		assertError(IllegalStateException.class,
				"Problematic entity (which is configured for the manipulator by the client code) is being referenced: entity.problematic");
	}

	// #############################################
	// ## . . . . . . . . Helpers . . . . . . . . ##
	// #############################################

	private void applyWithExpectedError(String... statements) {
		manipulationString = Stream.of(statements).collect(Collectors.joining("\r\n"));

		try {
			parseAndApply();

		} catch (Exception e) {
			exceptionClass = e.getClass();
			exceptionMessage = e.getMessage();
			return;
		}

		Assert.fail("Exception was expected");
	}

	private void assertError(Class<? extends Exception> clazz, String message) {
		Assertions.assertThat(exceptionMessage).isEqualTo(message);
		Assertions.assertThat(exceptionClass).isSameAs(clazz);
	}

	private void assertErrorFuzzy(Class<? extends Exception> clazz, String... messages) {
		Assertions.assertThat(exceptionClass).isSameAs(clazz);

		for (String message : messages)
			Assertions.assertThat(exceptionMessage).contains(message);
	}

	@Override
	protected MutableGmmlManipulatorParserConfiguration parserConfig() {
		MutableGmmlManipulatorParserConfiguration result = super.parserConfig();
		result.setBufferEntireInput(bufferEntireInput);
		result.setProblematicEntitiesRegistry(new StaticProblematicEntitiesRegistry(asSet("entity.problematic")));

		return result;
	}

}
