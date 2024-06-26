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
package edu.umd.cs.findbugs.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to suppress FindBugs warnings.<br/>
 * Since FindBugs analyzes byte code (and not sources), warnings cannot be suppressed using comments. For the same reason they cannot be suppressed
 * using {@link java.lang.SuppressWarnings}, because that annotation is ignored by the compiler ({@link java.lang.annotation.RetentionPolicy}
 * <code>SOURCE</code>). Therefore FindBugs uses its own annotation (<code>edu.umd.cs.findbugs.annotations.SuppressFBWarnings</code>) to surpress
 * warnings. Since we do not want to add another dependency only to be able to suppress warnings, this annotation just uses the same fully qualified
 * name. (It's not a copy of the FindBugs annotation, but it's compatible.)
 */
@Retention(RetentionPolicy.CLASS)
public @interface SuppressFBWarnings {
	String[] value();
}
