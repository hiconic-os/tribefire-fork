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
package com.braintribe.testing.category;

/**
 * Signals that the respective test is expected to take a relatively long time to execute (e.g. more than 10 seconds). This information can, for
 * example, be used by continuous integration pipelines to exclude these tests to process a pull request more quickly. The respective test will then
 * only be run in periodic builds where developers don't have to wait for the pipeline to finish.
 *
 * Whether or not to mark a test as <code>Slow</code> not only depends on the (expected) test execution time though, but also on the importance of the
 * test and on the total amount of tests in the respective artifact and artifact group. For example, if there 1000s tests, then even even an expected
 * execution time of a single second is quite much. On the other hand, if a developer decided to implement one big test that runs for a minute but
 * covers everything, then that test is a slow but also very important one. Furthermore total test execution time is still resonably fast, since it's
 * just one test.
 *
 * By marking a test as <code>Slow</code> one basically signals that the test is slow AND that it thus should be skipped when testing time is limited.
 *
 * @author michael.lafite
 *
 * @see VerySlow
 */
public interface Slow {
	// no methods required
}
