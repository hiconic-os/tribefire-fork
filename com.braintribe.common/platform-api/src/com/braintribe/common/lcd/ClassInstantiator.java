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
package com.braintribe.common.lcd;

/**
 * Provides methods to create new instances of a specified class. The purpose of this class is to avoid dependencies, for example when no Generic
 * Model features except entity creation are required.
 *
 * @param <T>
 *            super class for all types for which entity creation is supported. Note that this does not necessarily mean that creation is supported
 *            for all sub types!
 *
 * @author michael.lafite
 */
public interface ClassInstantiator<T> {

	/**
	 * Returns a new instance of the specified class. Please read the documentation of the implementing class to check which classes can be
	 * instantiated.
	 *
	 * @throws ClassInstantiationException
	 *             if the implementing class cannot create a new instance of the specified class (for whatever reason).
	 */
	<U extends T> U instantiate(Class<U> clazz) throws ClassInstantiationException;

	/**
	 * @see #instantiate(Class)
	 */
	T instantiate(String className) throws ClassInstantiationException;

	public static class ClassInstantiationException extends AbstractUncheckedBtException {

		private static final long serialVersionUID = -3959480387603696574L;

		public ClassInstantiationException(final String message, final Throwable cause) {
			super(message, cause);
		}

		public ClassInstantiationException(final String message) {
			super(message);
		}
	}
}
