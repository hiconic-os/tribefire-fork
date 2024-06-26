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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.gmview.client.UseCaseHandler;
import com.braintribe.gwt.gmview.metadata.client.SelectiveInformationResolver;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;

/**
 * This Codec is responsible for transforming an GenericEntity into String,
 * for visualization only purposes. It uses the GenericEntity selective information.
 * Notice that the encoding, due to some constraints in the GXT editors, may not return empty for a non null entity.
 * @author michel.docouto
 * 
 */
public class SimplifiedEntityRendererCodec implements Codec<GenericEntity, String>, UseCaseHandler {
	
	private String useCase;
	private boolean useShortType;
	
	/**
	 * Configures whether we should use the short type as default for the rendering the entity.
	 * Defaults to false.
	 */
	public void setUseShortType(boolean useShortType) {
		this.useShortType = useShortType;
	}
	
	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}
	
	@Override
	public String getUseCase() {
		return useCase;
	}
	
	@Override
	public String encode(GenericEntity entity) throws CodecException {
		if (entity == null)
			return "";
		
		EntityType<GenericEntity> entityType = entity.entityType();
		if (useShortType)
			return entityType.getShortName();
		
		String selectiveInformation = SelectiveInformationResolver.resolve(entityType, entity, (ModelMdResolver) null, useCase/*, null*/);
		if (selectiveInformation != null && !selectiveInformation.isEmpty())
			return selectiveInformation;
		else
			return entityType.getShortName();
	}

	@Override
	public GenericEntity decode(String encodedValue) throws CodecException {
		throw new CodecException("Decode is not supported");
	}

	@Override
	public Class<GenericEntity> getValueClass() {
		return GenericEntity.class;
	}

}
