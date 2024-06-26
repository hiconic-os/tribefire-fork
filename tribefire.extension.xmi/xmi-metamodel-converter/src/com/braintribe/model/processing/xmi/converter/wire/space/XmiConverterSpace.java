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
package com.braintribe.model.processing.xmi.converter.wire.space;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.braintribe.model.processing.xmi.converter.XmiToMetaModelCodec;
import com.braintribe.model.processing.xmi.converter.experts.ClasspathResourceStringProvider;
import com.braintribe.model.processing.xmi.converter.wire.contract.XmiConverterContract;
import com.braintribe.web.velocity.renderer.VelocityTemplateRenderer;
import com.braintribe.wire.api.annotation.Managed;

/**
 * @author pit
 *
 */
@Managed
public class XmiConverterSpace implements XmiConverterContract {
	
	@Managed(com.braintribe.wire.api.annotation.Scope.prototype)
	private Supplier<String> templateSupplier(String key) {
		ClasspathResourceStringProvider bean = new ClasspathResourceStringProvider(key);
		return bean;
	}

	@Managed
	private VelocityTemplateRenderer renderer() {
		VelocityTemplateRenderer bean = new VelocityTemplateRenderer();
		Map<String, Supplier<String>> map = new HashMap<>();
		
		Stream.of(
			"main",
			"model",
			"package",
			"class",
			"enum",
			"attribute",
			"association",
			"associationClass",
			"generalization",
			"tagReference",
			"stereotype",
			"tagDefinition",
			"simpletypes",
			"namespaceOwned",
			"node"
		).forEach(n -> map.put(n, templateSupplier("templates/" + n + ".template.vm")));
		
		bean.setKeyToProviderMap( map);
		return bean;
	}
	
	@Managed
	public XmiToMetaModelCodec xmiConverter() {
		XmiToMetaModelCodec bean = new XmiToMetaModelCodec();
		bean.setRenderer(renderer());
		return bean;
	}
	
}
