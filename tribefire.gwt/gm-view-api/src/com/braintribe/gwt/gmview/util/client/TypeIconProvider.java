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
package com.braintribe.gwt.gmview.util.client;

import java.util.Map;

import com.braintribe.gwt.gmview.client.IconAndType;
import com.braintribe.gwt.gmview.client.IconProvider;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

/**
 * Icon provider based on types.
 * @author michel.docouto
 *
 */
public class TypeIconProvider implements IconProvider {
	
	private Map<String, IconProvider> typeProvidersMap;
	private IconProvider defaultProvider;
	private PersistenceGmSession gmSession;
	private String useCase;
	
	/**
	 * Configures the required map for providers per type.
	 */
	@Required
	public void setTypeProvidersMap(Map<String, IconProvider> typeProvidersMap) {
		this.typeProvidersMap = typeProvidersMap;
	}
	
	/**
	 * Configures the required default provider to be used when there is no match to the providers defined via {@link #setTypeProvidersMap(Map)}.
	 */
	@Configurable
	public void setDefaultProvider(IconProvider defaultProvider) {
		this.defaultProvider = defaultProvider;
	}
	
	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}
	
	@Override
	public PersistenceGmSession getGmSession() {
		return gmSession;
	}
	
	@Override
	public String getUseCase() {
		return useCase;
	}

	@Override
	public IconAndType apply(ModelPath modelPath) {
		if (modelPath != null) {
			String typeSignature;
			if (modelPath.last().getValue() instanceof GmEntityType)
				typeSignature = ((GmEntityType) modelPath.last().getValue()).getTypeSignature();
			else
				typeSignature = modelPath.last().getType().getTypeSignature();

			IconProvider provider = typeProvidersMap.get(typeSignature);
			if (provider != null) {
				provider.configureGmSession(gmSession);
				provider.configureUseCase(useCase);
				IconAndType iconAndType = provider.apply(modelPath);
				if (iconAndType != null)
					return iconAndType;
			}
		}
		
		if (defaultProvider != null) {
			defaultProvider.configureGmSession(gmSession);
			defaultProvider.configureUseCase(useCase);
			return defaultProvider.apply(modelPath);
		}
		
		return null;
	}

}
