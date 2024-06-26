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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.gmview.client.UseCaseHandler;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.data.prompt.Name;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;

import com.braintribe.utils.i18n.I18nTools;

public class GmEnumConstantDisplayCodec implements Function<GmEnumConstant, String>, UseCaseHandler {
	private static Logger logger = new Logger(GmEnumConstantDisplayCodec.class);

	private ModelMdResolver metaDataResolver;
	private String useCase;
	private Codec<GmEnumConstant, String> enumConstantRenderer;
	
	public void configureMetaDataResolver(ModelMdResolver metaDataResolver) {
		this.metaDataResolver = metaDataResolver;
	}
	
	public void configureEnumConstantRenderer(Codec<GmEnumConstant, String> enumConstantRenderer) {
		this.enumConstantRenderer = enumConstantRenderer;
	}
	
	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}

	@Override
	public String apply(GmEnumConstant enumConstant) throws RuntimeException {
		String display = enumConstant.getName();
		Name name = null;
		if (metaDataResolver != null)
			name = metaDataResolver.lenient(true).enumConstant(enumConstant).useCase(useCase).meta(Name.T).exclusive();
		if (name != null && name.getName() != null)
			display = I18nTools.getLocalized(name.getName());
		else if (enumConstantRenderer != null) {
			try {
				display = enumConstantRenderer.encode(enumConstant);
			} catch (CodecException e) {
				logger.error("Error while encoding enumConstant '" + display + "'", e);
				e.printStackTrace();
			}
		}
		
		return display;
	}
	
	@Override
	public String getUseCase() {
		return useCase;
	}

}
