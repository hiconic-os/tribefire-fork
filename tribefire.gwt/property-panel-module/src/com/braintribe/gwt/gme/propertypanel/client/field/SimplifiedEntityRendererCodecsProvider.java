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
package com.braintribe.gwt.gme.propertypanel.client.field;

import java.util.function.Supplier;

import com.braintribe.codec.Codec;
import com.braintribe.gwt.codec.registry.client.CodecEntry;
import com.braintribe.gwt.codec.registry.client.CodecRegistry;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;

/**
 * This Function is responsible for providing codecs based on the given Class.
 * @author michel.docouto
 *
 */
public class SimplifiedEntityRendererCodecsProvider extends CodecRegistry<String> {
	
	private CodecRegistry<String> codecRegistry;
	private Supplier<SimplifiedEntityRendererCodec> simplifiedEntityFieldRendererCodecSupplier;
	
	/**
	 * Configures the required {@link CodecRegistry} used as renderers.
	 */
	@Required
	public void setCodecRegistry(CodecRegistry<String> codecRegistry) {
		this.codecRegistry = codecRegistry;
	}
	
	/**
	 * Configures the simplified entity renderer codec.
	 */
	@Required
	public void setSimplifiedEntityFieldRendererCodec(Supplier<SimplifiedEntityRendererCodec> simplifiedEntityFieldRendererCodecSupplier) {
		this.simplifiedEntityFieldRendererCodecSupplier = simplifiedEntityFieldRendererCodecSupplier;
	}
	
	@Override
	public <T> Codec<T, String> getCodec(Class<?> clazz) {
		Codec<T, String> codec = codecRegistry.getCodec(clazz);
		if (codec != null)
			return codec;
		
		if (!isGenericEntityInstance(clazz))
			return null;
		
		return (Codec<T, String>) simplifiedEntityFieldRendererCodecSupplier.get();
	}
	
	@Override
	public CodecEntry<String> getCodecEntry(Class<?> clazz) {
		CodecEntry<String> codecEntry = codecRegistry.getCodecEntry(clazz);
		if (codecEntry != null)
			return codecEntry;
		
		if (!isGenericEntityInstance(clazz))
			return null;
		
		return new CodecEntry<>(clazz, simplifiedEntityFieldRendererCodecSupplier);
	}

	private boolean isGenericEntityInstance(Class<?> clazz) {
		return GMF.getTypeReflection().getType(clazz) instanceof EntityType<?>;
	}

}
