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
package com.braintribe.utils.lcd;

/**
 * An extended version of {@link StopWatch} that resets the instance after each call to {@link #getElapsedTime()}.
 *
 *
 */
public class AutoResettingStopWatch extends StopWatch {

	public AutoResettingStopWatch() {
		// nothing to do
	}

	/**
	 * Returns the elapsed time and resets the internal timer.
	 */
	@Override
	public long getElapsedTime() {
		final long elapsedTime = super.getElapsedTime();
		reset();
		return elapsedTime;
	}
}
