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
package com.braintribe.model.processing.crypto.token.loader;

import com.braintribe.model.crypto.key.SecretKey;

/**
 * <p>
 * A {@link KeyLoader} which loads {@link javax.crypto.SecretKey} objects based on {@link SecretKey}
 * instances.
 * 
 */
public interface SecretKeyLoader<I extends SecretKey, O extends javax.crypto.SecretKey> extends KeyLoader<I, O> {
	// A marker interface, so far.
}
