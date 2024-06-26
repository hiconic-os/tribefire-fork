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
package com.braintribe.common.lcd.function;

import java.util.function.Consumer;

/**
 * Represents a functions which accepts one input and returns a result of the same type. The result would typically be a modified copy of the input
 * value.
 * <p>
 * This can be used when some inner parts of an object have to be completed / resolved / decrypted just-in-time.
 *
 * @author peter.gazdik
 */
public interface GenericTransformer {

	// @formatter:off
	GenericTransformer identityTransformer = new GenericTransformer() {@Override public <T> T transform(T t) { return t; } }; 
	// @formatter:on

	<T> T transform(T t);

	default <T> void transform(T t, Consumer<? super T> consumer) {
		consumer.accept(transform(t));
	}

}
