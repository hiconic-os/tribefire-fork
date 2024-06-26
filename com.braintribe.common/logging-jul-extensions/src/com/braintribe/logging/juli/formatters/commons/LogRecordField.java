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
package com.braintribe.logging.juli.formatters.commons;

import java.util.Arrays;

import com.braintribe.logging.juli.formatters.json.JsonFormatter;
import com.braintribe.logging.juli.formatters.simple.SimpleFormatter;

/**
 * Represents a log record field, e.g. date, level or message.
 *
 * @author michael.lafite
 *
 * @see SimpleFormatter
 * @see JsonFormatter
 */
public enum LogRecordField {
	DATE(1, "date"),
	SOURCE(2, "source"),
	LOGGERNAME(3, "loggerName"),
	LEVEL(4, "level"),
	MESSAGE(5, "message"),
	THROWABLE(6, "throwable"),
	SHORTLOGGERNAME(7, "shortLoggerName"),
	LOGGERNAMESHORTPACKAGES(8, "loggerNameShortPackages"),
	NDC(9, "ndc"),
	MDC(10, "mdc"),
	SOURCE_AND_LOGGER(11, "sourceAndLogger"),
	SOURCE_CLASS_NAME(12, "sourceClassName"),
	CONDENSED_THROWABLE(13, "condensedThrowable"),
	ISO8601UTC(14, "iso8601Utc"),
	THREAD(15, "thread"),
	MODULE(16, "module");

	private int index;
	private String name;

	private static LogRecordField[] fieldsByIndex = new LogRecordField[LogRecordField.values().length];

	LogRecordField(int index, String name) {
		this.index = index;
		this.name = name;
	}

	/* We can't access any static field from the constructor, since enum values are instiated very early. Instead, we use a static initializer, which
	 * (just for enums) is called AFTER the constructor. */
	static {
		Arrays.asList(LogRecordField.values()).forEach(field -> fieldsByIndex[field.getIndex() - 1] = field);
	}

	/**
	 * Returns the field's <code>index</code> as used in {@link SimpleFormatter#format(java.util.logging.LogRecord)}.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the field's <code>name</code> as used in {@link JsonFormatter#format(java.util.logging.LogRecord)}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the field with the specified <code>index</code>.
	 *
	 * @throws IllegalArgumentException
	 *             if there is no field with the specified <code>index</code>.
	 * @see #getIndex()
	 */
	public static LogRecordField byIndex(int index) {
		try {
			return fieldsByIndex[index - 1];
		} catch (IndexOutOfBoundsException e) { // NOSONAR: no need to propagate exception
			throw new IllegalArgumentException("Index " + index + " is invalid. Allowed values are 1 to " + fieldsByIndex.length + ".");
		}
	}
}
