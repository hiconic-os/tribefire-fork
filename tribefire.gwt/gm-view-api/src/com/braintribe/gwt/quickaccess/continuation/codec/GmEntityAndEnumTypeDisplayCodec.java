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
package com.braintribe.gwt.quickaccess.continuation.codec;

import java.util.function.Function;

import com.braintribe.gwt.gmview.client.UseCaseHandler;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.data.prompt.Name;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;

import com.braintribe.utils.i18n.I18nTools;

public class GmEntityAndEnumTypeDisplayCodec implements Function<GmType, String>, UseCaseHandler {
	
	private ModelMdResolver metaDataResolver;
	private String useCase;
	
	public void configureMetaDataResolver(ModelMdResolver metaDataResolver) {
		this.metaDataResolver = metaDataResolver;
	}
	
	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}
	
	@Override
	public String apply(GmType type) throws RuntimeException {
		if (type instanceof GmEntityType) {
			String entityTypeDisplay = GMEMetadataUtil
					.getEntityTypeDisplay(metaDataResolver != null ? metaDataResolver.entityType((GmEntityType) type).useCase(useCase) : null);
			if (entityTypeDisplay == null)
				entityTypeDisplay = GMEUtil.getShortName(type);
			
			return entityTypeDisplay;
		} else {
			GmEnumType enumType = (GmEnumType) type;
			String enumTypeDisplay = GMEUtil.getShortName(enumType);
			if (metaDataResolver != null) {
				Name name = metaDataResolver.enumTypeSignature(enumType.getTypeSignature()).useCase(useCase).meta(Name.T)
						.exclusive();
				if (name != null && name.getName() != null)
					enumTypeDisplay = I18nTools.getLocalized(name.getName());
			}
			
			return enumTypeDisplay;
		}
	}
	
	@Override
	public String getUseCase() {
		return useCase;
	}

}
