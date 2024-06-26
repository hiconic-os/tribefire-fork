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
package com.braintribe.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import com.braintribe.common.lcd.Constants;

/**
 * This class may contain Java-only (i.e. GWT incompatible) code. For further information please see {@link com.braintribe.utils.lcd.ThrowableTools}.
 *
 * @author michael.lafite
 */
public final class ThrowableTools extends com.braintribe.utils.lcd.ThrowableTools {

	private ThrowableTools() {
		// no instantiation required
	}

	/**
	 * Returns a string containing the output of {@link Throwable#printStackTrace(PrintWriter)}.
	 */
	public static String getStackTraceString(final Throwable throwable) {
		if (throwable == null) {
			return null;
		}
		try {
			final StringWriter stringWriter = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(stringWriter);
			throwable.printStackTrace(printWriter);
			printWriter.flush();
			return stringWriter.toString();
		} catch (Exception ex) {
			// ignored / shouldn't happen
		}
		return null;
	}

	/**
	 * Recursively searches the <code>searchedCauseType</code> in the <code>Throwable</code> chain (i.e. {@link Throwable#getCause()}).
	 */
	public static <T extends Throwable> T searchCause(final Throwable throwable, final Class<T> searchedThrowableType) {
		if (throwable == null) {
			return null;
		}
		if (searchedThrowableType.isInstance(throwable)) {
			@SuppressWarnings("unchecked")
			final T searchedThrowable = (T) throwable;
			return searchedThrowable;
		}
		return searchCause(throwable.getCause(), searchedThrowableType);
	}

	/**
	 * Gets a description of the passed <code>Exception</code> containing the exception class, message and stacktrace (only of the passed
	 * <code>throwable</code>, not of it's {@link Throwable#getCause() cause}.
	 *
	 * @param throwable
	 *            the <code>Throwable</code> to process.
	 * @return the string containing the description.
	 */
	public static String getDescription(final Throwable throwable) {
		final String lineSeparator = Constants.lineSeparator();

		// not GWT compatible (apart from that method should also work in GWT)
		final String threadName = Thread.currentThread().getName();

		final StringBuilder builder = new StringBuilder(2048);
		builder.append(ReflectionTools.getSimpleName(throwable.getClass()) + " in thread " + CommonTools.getStringRepresentation(threadName)
				+ ". Message: " + CommonTools.getStringRepresentation(throwable.getMessage()));
		StringTools.append(builder, lineSeparator, throwable.getClass().getName(), lineSeparator);
		final StackTraceElement[] stackTrace = throwable.getStackTrace();
		for (final StackTraceElement element : stackTrace) {
			StringTools.append(builder, "\t", element.toString(), lineSeparator);
		}
		return builder.toString();
	}

	/**
	 * Returns the message of the root cause throwable of the passed throwable.
	 */
	public static String getLastMessage(Throwable throwable) {
		if (throwable == null) {
			return null;
		}

		Set<Throwable> seenThrowables = new HashSet<>();
		seenThrowables.add(throwable);

		String message = throwable.getMessage();
		while ((throwable = throwable.getCause()) != null) {
			if (!seenThrowables.add(throwable)) {
				// This is a recursion; leave the loop now
				break;
			}
			if (throwable.getMessage() != null) {
				message = throwable.getMessage();
			}
		}

		return message;
	}

}
