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
package jsinterop.utils;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jsinterop.annotations.JsType;

/**
 * @author peter.gazdik
 */
@JsType(namespace = "$tf.util")
public class Collections {

	public static <T> List<T> list(T... ts) {
		return new ArrayList<>(asList(ts));
	}

	public static <T> Set<T> set(T... ts) {
		return new LinkedHashSet<>(asList(ts));
	}

}
