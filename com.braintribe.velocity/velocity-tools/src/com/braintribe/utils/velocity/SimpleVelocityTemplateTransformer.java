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
package com.braintribe.utils.velocity;


import com.braintribe.common.lcd.transformer.Transformer;
import com.braintribe.common.lcd.transformer.TransformerException;
import com.braintribe.utils.Not;

/**
 * A simple {@link Transformer} that renders velocity templates using {@link SimpleVelocityTemplateRenderer}.
 * 
 * @author michael.lafite
 */
public class SimpleVelocityTemplateTransformer implements Transformer<String, String, Object> {

	private SimpleVelocityTemplateRenderer templateRenderer;

	public SimpleVelocityTemplateTransformer() {
		// nothing to do
	}
	
	public SimpleVelocityTemplateRenderer getTemplateRenderer() {
		if (this.templateRenderer == null) {
			this.templateRenderer = new SimpleVelocityTemplateRenderer();
		}
		return Not.Null(this.templateRenderer);
	}

	public void setTemplateRenderer(final SimpleVelocityTemplateRenderer templateRenderer) {
		this.templateRenderer = templateRenderer;
	}

	
	@Override
	public String transform(final String input, final Object transformationContext) throws TransformerException {
		if (input == null) {
			throw new TransformerException("The passed input must not be null!");
		}

		getTemplateRenderer().setTemplateProvider(k -> input);
		return getTemplateRenderer().apply(transformationContext);
	}
}
