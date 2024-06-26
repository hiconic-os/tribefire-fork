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
package com.braintribe.testing.internal.path;

/**
 * ATTENTION: this class will be moved soon, which means the package name will change!<br>
 * <p>
 * Specifies whether the path is supposed to (not) exist.
 *
 * @author michael.lafite
 */
public enum PathExistence {
	/**
	 * The path must already exist.
	 */
	MustExist,
	/**
	 * The path may or may not exist.
	 */
	MayExist,
	/**
	 * The path must not exist.
	 */
	MustNotExist;
}
