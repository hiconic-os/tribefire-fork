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
package com.braintribe.crypto.key.provider;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.braintribe.codec.Codec;


/**
 * <p>Provides {@link com.braintribe.codec.Codec}(s) capable of importing/exporting 
 *    {@link Key}(s) from/to byte arrays.
 * 
 * <p>The provided codec shall import and export keys in accordance with the key algorithm passed 
 *    as argument to the {@link Function#apply(Object)} method.
 * 
 *
 */
public abstract class KeyCodecProvider<T extends Key> implements Function<String, Codec<T, byte[]>> {

	private Map<String, Codec<T, byte[]>> keyExporters = new HashMap<String, Codec<T, byte[]>>();
	
	protected abstract Codec<T, byte[]> createExporter(String algorithm) throws RuntimeException;
	
	protected Codec<T, byte[]> provide(Class<T> type, String algorithm)  throws RuntimeException {
		String i = exporterId(type, algorithm);
		
		Codec<T, byte[]> exporter = keyExporters.get(i);
		
		if (exporter != null) {
			return exporter;
		}
		
		exporter = createExporter(algorithm);
		
		keyExporters.put(i, exporter);
		
		return exporter;
		
	}
	
	private String exporterId(Class<T> type, String algorithm) {
		return type.getSimpleName()+"."+algorithm;
	}
	
}
