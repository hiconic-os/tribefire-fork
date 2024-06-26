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
package tribefire.extension.wopi.test.validator;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.braintribe.utils.system.exec.CommandExecutionImpl;
import com.braintribe.utils.system.exec.ProcessTerminatorImpl;

import tribefire.extension.wopi.test.AbstractWopiTest;

/**
 * 
 *
 */
//@formatter:off
// get groups:
// -----------
// xmllint --xpath '/WopiValidation/TestGroup/@Name' TestCases.xml | tr ' ' '\n' | sed 's/Name="//g' | sed 's/"$//g'
// get tests per group:
// --------------------
// xmllint --xpath '/WopiValidation/TestGroup[@Name="FileVersion"]' TestCases.xml
// xmllint --xpath '/WopiValidation/TestGroup/TestCases/TestCase/@Name' TestCases.xml | tr ' ' '\n' |sed 's/Name="//g' | sed 's/"$//g' > /tmp/out.txt
//@formatter:on
@RunWith(Parameterized.class)
public abstract class AbstractValidatorWopiTest extends AbstractWopiTest {

	protected static CommandExecutionImpl commandExection;

	static {
		ProcessTerminatorImpl processTerminator = new ProcessTerminatorImpl();
		commandExection = new CommandExecutionImpl();
		commandExection.setProcessTerminator(processTerminator);

	}

	protected String testName;
	protected List<String> commands;

	public AbstractValidatorWopiTest(@SuppressWarnings("unused") String testGroup, String testName) {
		this.testName = testName;
	}

}
