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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Provides methods that ensure the returned value is not <code>null</code>. <b>Attention:</b>: The methods use Java assertions for their
 * <code>null</code> checks, which means the checks won't be executed in many development environments and almost never in production
 * environments.<br>
 * The initial purpose of this class was to be used as a helper when working with null annotations and advanced null analysis (which is e.g. supported
 * by Eclipse) in combination with libraries that don't use these null annotations. In that case one could pass results of library methods to
 * <code>NotNull.get</code> to tell the IDE that the result cannot be <code>null</code>. Example:
 *
 * <pre>
 * String path = NotNull.get(new File("relative/path/to/file").getAbsolutePath());
 * // The IDE knows that path cannot be null
 * ...
 * </pre>
 *
 * The class was intended to be used primarily for null analysis and also only for methods where it's clear they won't return <code>null</code>, e.g.
 * <code>File.getAbsolutePath</code>. Therefore it also made sense to just Java ssertions for the checks. However, Since we decided not to use null
 * analysis for now (mainly due to these problems with 3rd party libraries), this class is deprecated now (see deprecation comment for more info),
 * also because some developers apparently used it in places where <code>null</code> values can occur.
 *
 * @author michael.lafite
 *
 * @deprecated Either use {@link Not#Null(Object)}, which throws exceptions instead of relying on assertions, or completely remove the
 *             <code>NotNull</code> call, if the passed value cannot be <code>null</code> anyway.
 */
@Deprecated
public final class NotNull {

	private NotNull() {
		// no instantiation required
	}

	public static <T> T get(final T object) {
		assert (object != null) : "the passed object is null!";
		return object;
	}

	@SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
	public static <T> String toString(final T object) {
		final String result = get(object).toString();
		assert (result != null) : "The string representation of the passed object is null! "
				+ CommonTools.getParametersString("object type", object.getClass().getName());
		return result;
	}
}
