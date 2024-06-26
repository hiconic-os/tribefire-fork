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
package com.braintribe.gwt.codec.registry.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.ioc.client.Configurable;

public class CodecRegistry<E> {
	private Map<Class<?>, CodecEntry<E>> codecEntries = new HashMap<Class<?>, CodecEntry<E>>();
	
	@Configurable
	public void setCodecMap(Map<Class<?>, Supplier<? extends Codec<?, E>>> codecMap) {
		codecEntries.clear();
		
		for (Map.Entry<Class<?>, Supplier<? extends Codec<?, E>>> entry: codecMap.entrySet()) {
			Class<?> clazz = entry.getKey();
			Supplier<? extends Codec<?, E>> codecSupplier = entry.getValue();
			
			CodecEntry<E> codecEntry = new CodecEntry<E>(clazz, codecSupplier);
			codecEntries.put(clazz, codecEntry);
		}
	}
	
	public CodecEntry<E> requireCodecEntry(Class<?> clazz) throws CodecException {
		CodecEntry<E> codecEntry = getCodecEntry(clazz);
		
		if (codecEntry != null)
			return codecEntry;
		
		throw new CodecException("no codec registered for class " +  clazz);
	}
	
	public <T> Codec<T, E> requireCodec(Class<?> clazz) throws CodecException {
		Codec<T, E> codec = getCodec(clazz);
		
		if (codec != null)
			return codec;
		
		throw new CodecException("no codec registered for class " +  clazz);
	}
	
	public CodecEntry<E> getCodecEntry(Class<?> clazz) {
		return codecEntries.get(clazz);
	}
	
	public <T> Codec<T, E> getCodec(Class<?> clazz) {
		CodecEntry<E> codecEntry = codecEntries.get(clazz);
		if (codecEntry != null)
			return getCodecFromSupplier(codecEntry.getCodec());
		
		return null;
	}
	
	private <T> Codec<T, E> getCodecFromSupplier(Supplier<Codec<T, E>> supplier) {
		Codec<T, E> codec = supplier.get();
		if (codec instanceof CodecRegistryAware) {
			CodecRegistryAware<E> codecRegistryAware = (CodecRegistryAware<E>) codec;
			codecRegistryAware.intializeCodecRegistry(this);
		}
		
		return codec;
	}
}
