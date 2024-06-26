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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.braintribe.common.lcd.Numbers;
import com.braintribe.common.lcd.Pair;
import com.braintribe.utils.DateTools;

/**
 * <p>
 * A helper class to perform time measurements.
 * </p>
 * <b>Example:</b>
 *
 * <pre>
 * StopWatch stopWatch = new StopWatch();
 * // compute something
 * long elapsedTimeInMs = stopWatch.getElapsedTime();
 * System.out.println(&quot;The computation took &quot; + elapsedTimeInMs + &quot;ms&quot;);
 * </pre>
 *
 *
 * @author michael.lafite
 */
public class StopWatch {

	private long startTime;

	private boolean automaticResetEnabled = false;

	private long lastElapsedTime = -1;

	private long lastIntermediateResult = -1;
	private List<Pair<Long,String>> intermediateResults;
	
	/**
	 * Creates a new instance of this class and sets the start time.
	 */
	public StopWatch() {
		reset();
	}

	
	public void intermediate(String description) {
		if (intermediateResults == null) {
			intermediateResults = new LinkedList<>();
		}
		long now = System.currentTimeMillis();
		long timeSinceStart = lastIntermediateResult != -1 ? now - lastIntermediateResult : now - startTime;
		lastIntermediateResult = now;

		intermediateResults.add(new Pair<>(timeSinceStart, description));
	}
	
	@Override
	public String toString() {
		final long elapsedTime = System.currentTimeMillis() - this.startTime;
		Date started = new Date(startTime);
		String startedString = DateTools.encode(started, DateTools.ISO8601_DATE_WITH_MS_FORMAT);
		String elapsedString = StringTools.prettyPrintMilliseconds(elapsedTime, true);

		StringBuilder sb = new StringBuilder("StopWatch[Started:");
		sb.append(startedString);
		sb.append(",running:");
		sb.append(elapsedString);
		sb.append('(');
		sb.append(elapsedTime);
		sb.append(" ms)");
		if (intermediateResults != null) {
			for (Pair<Long,String> i : intermediateResults) {
				sb.append(",");
				sb.append(i.second());
				sb.append(":");
				sb.append(StringTools.prettyPrintMilliseconds(i.first(), true));
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Resets the start time.
	 */
	public final void reset() {
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Gets the elapsed time in milliseconds since the instance was created or reset.
	 */
	public long getElapsedTime() {
		final long result = System.currentTimeMillis() - this.startTime;
		if (this.automaticResetEnabled) {
			reset();
		}
		this.lastElapsedTime = result;
		return result;
	}

	/**
	 * Returns the same result as method {@link #getElapsedTime()} returned when it was invoked the last time. If it
	 * hasn't been invoked yet, this method invokes it. A {@link #reset() reset} does not affect the result of this
	 * method.
	 */
	public long getLastElapsedTime() {
		if (this.lastElapsedTime == -1) {
			getElapsedTime();
		}
		return this.lastElapsedTime;
	}

	/**
	 * Gets the elapsed time in seconds.
	 *
	 * @see #getElapsedTime()
	 */
	public long getElapsedTimeInSeconds() {
		return getElapsedTime() / Numbers.THOUSAND;
	}

	/**
	 * Gets the elapsed time in minutes.
	 *
	 * @see #getElapsedTime()
	 */
	public long getElapsedTimeInMinutes() {
		return getElapsedTimeInSeconds() / Numbers.SECONDS_PER_MINUTE;
	}

	public boolean isAutomaticResetEnabled() {
		return this.automaticResetEnabled;
	}

	/**
	 * If enabled, the start time will be reset when getting the elapsed time.
	 */
	public void setAutomaticResetEnabled(final boolean automaticResetEnabled) {
		this.automaticResetEnabled = automaticResetEnabled;
	}

}
