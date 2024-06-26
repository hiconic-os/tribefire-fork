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
package com.braintribe.devrock.zed.context;

import java.util.Map;

import com.braintribe.cfg.Configurable;
import com.braintribe.devrock.zed.api.context.ZedAnalyzerTypeReferenceContext;
import com.braintribe.zarathud.model.data.TypeReferenceEntity;

public class BasicZedAnalyzerTypeReferenceContext implements ZedAnalyzerTypeReferenceContext{
	private Map<String,TypeReferenceEntity> templateTypes;

	public BasicZedAnalyzerTypeReferenceContext(Map<String, TypeReferenceEntity> templateTypes) {
		this.templateTypes = templateTypes;
	}

	@Configurable
	public void setTemplateTypes(Map<String, TypeReferenceEntity> templateTypes) {
		this.templateTypes = templateTypes;
	}
	
	@Override
	public Map<String, TypeReferenceEntity> templateTypes() {
		return templateTypes;
	}

	
}
