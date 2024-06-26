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
package com.braintribe.codec;

import java.util.List;

/**
 * This special codec is configured with a list of codecs.
 * It tries to decode/encode with the first one in the list.
 * If a CodecException occurs it then uses the second one and so on.
 * 
 * @author michel.docouto
 *
 */
public class FallbackCodec<T, E> implements Codec<T, E> {
	private Class<T> valueClass;
	protected List<? extends Codec<T, E>> codecs;
	private boolean ignoreRuntimeExceptions = false;
	
	public FallbackCodec(Class<T> valueClass) {
		this.valueClass = valueClass;
	}
	
	public void setCodecs(List<? extends Codec<T, E>> codecs) {
		this.codecs = codecs;
	}
	
	/**
	 * Configures whether we should ignore runtime exceptions (and not only CodecException), when using the codecs.
	 * Defaults to false.
	 */
	public void setIgnoreRuntimeExceptions(boolean ignoreRuntimeExceptions) {
		this.ignoreRuntimeExceptions = ignoreRuntimeExceptions;
	}

	@Override
	public T decode(E encodedValue) throws CodecException {
		T decodedValue = null;
		Exception lastException = null;
		for (Codec<T, E> codec : codecs) {
			try {
				decodedValue = codec.decode(encodedValue);
				return decodedValue;
			} catch (CodecException ex) {
				//Ignore
				lastException = ex;
			} catch (RuntimeException ex) {
				if (ignoreRuntimeExceptions) {
					//Ignore
					lastException = ex;
				} else
					throw ex;
			}
		}
		
		throw new CodecException("All configured codecs failed to decode the given encodedValue.", lastException);
	}

	@Override
	public E encode(T value) throws CodecException {
		E encodedValue = null;
		Exception lastException = null;
		for (Codec<T, E> codec : codecs) {
			try {
				encodedValue = codec.encode(value);
				return encodedValue;
			} catch (CodecException ex) {
				//Ignore
				lastException = ex;
			} catch (RuntimeException ex) {
				if (ignoreRuntimeExceptions) {
					//Ignore
					lastException = ex;
				} else
					throw ex;
			}
		}
		
		throw new CodecException("All configured codecs failed to encode the given value.", lastException);
	}

	@Override
	public Class<T> getValueClass() {
		return valueClass;
	}

}
