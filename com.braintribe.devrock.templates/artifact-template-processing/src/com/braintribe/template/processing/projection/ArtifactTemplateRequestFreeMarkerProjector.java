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
package com.braintribe.template.processing.projection;

import static com.braintribe.utils.lcd.CollectionTools2.asMap;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.braintribe.devrock.templates.model.ArtifactTemplateRequest;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.config.api.ModeledConfiguration;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.SimpleType;
import com.braintribe.template.processing.api.ArtifactTemplateRequestProjector;
import com.braintribe.template.processing.projection.support.TemplateSupport;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * 
 * Using FreeMarker, projects the entity property values.
 * 
 */
public class ArtifactTemplateRequestFreeMarkerProjector implements ArtifactTemplateRequestProjector {

	private Version freeMarkerVersion;
	private ModeledConfiguration modeledConfiguration;
	
	public ArtifactTemplateRequestFreeMarkerProjector(Version freeMarkerVersion, ModeledConfiguration modeledConfiguration) {
		this.freeMarkerVersion = freeMarkerVersion;
		this.modeledConfiguration = modeledConfiguration;
	}

	@Override
	public void project(ArtifactTemplateRequest request) {
		Map<String, Object> dataModel = asMap("request", request, "support", new TemplateSupport(modeledConfiguration));

		Configuration freeMarkerConfig = new Configuration(freeMarkerVersion);

		for (Property property : request.entityType().getProperties()) {
			if (property.getType().compareTo(SimpleType.TYPE_STRING) == 0 && property.get(request) != null) {
				try {
					property.set(request, processStringWithFreeMarker(freeMarkerConfig, property.get(request), dataModel));
				} catch (Exception e) {
					throw Exceptions.unchecked(e, "Failed while processing '" + property.get(request) + "' with FreeMarker.");
				}
			}
		}
	}

	private String processStringWithFreeMarker(Configuration freeMarkerConfig, String templatedString,
			Map<String, Object> dataModel) throws Exception {
		String result = null;
		try (StringReader in = new StringReader(templatedString); Writer out = new StringWriter()) {
			Template template = new Template("", in, freeMarkerConfig);
			template.process(dataModel, out);
			result = out.toString();
		}
		return result;
	}
	
}
