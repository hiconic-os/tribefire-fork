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
package com.braintribe.utils.template.model;

import java.util.function.Function;

import com.braintribe.logging.Logger;
import com.braintribe.utils.template.TemplateException;

public class MergeContext {
	
	private static Logger logger = Logger.getLogger(MergeContext.class);
			
	private boolean sourceMode;
	private Function<String, ?> variableProvider;
	private Function<String, String> literalEscaper;
	
	public void setLiteralEscaper(Function<String, String> literalEscaper) {
		this.literalEscaper = literalEscaper;
	}
	
	public void setVariableProvider(Function<String, ?> variableProvider) {
		this.variableProvider = variableProvider;
	}
	
	public void setSourceMode(boolean sourceMode) {
		this.sourceMode = sourceMode;
	}
	
	public boolean isSourceMode() {
		return sourceMode;
	}
	
	public Function<String, ?> getVariableProvider() {
		return variableProvider;
	}
	
	public Object getVariableValue(String variableName) throws TemplateException {
		try {
			Object result = variableProvider.apply(variableName);
			if (result == null) {
				logger.info(() -> "The variable "+variableName+" could not be resolved to an actual value.");
			}
			return result;
		} catch (RuntimeException e) {
			throw new TemplateException("error while accessing variable " + variableName , e);
		}
	}
	
	public String createSourceLiteral(String text) throws TemplateException {
		if (literalEscaper != null) {
			try {
				text = literalEscaper.apply(text);
			} catch (RuntimeException e) {
				throw new TemplateException("error while escaping literal string", e);
			}
		}
			
		return '"' + text + '"';
	}
}
