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
package tribefire.extension.xml.schemed.xsd.mapper.overrides;

import java.util.HashMap;
import java.util.Map;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.MappingOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;


public class MappingOverrider  {
	private Map<String,String> xsdNamesToOveride = new HashMap<String, String>();

	public MappingOverrider(MappingOverride ... overrides) {
		for (MappingOverride override : overrides) {
			SchemaAddress address = override.getSchemaAddress();
			String property = address.getElement();
			String parent = address.getParent();
			String key;
			if (property == null) {
				key = parent;			
			}
			else {
				 key = parent + "." + property;
			}
			xsdNamesToOveride.put(key,  override.getNameOverride());
		}		
	}
	

	public String getOverride(String schemaAddress) throws RuntimeException {
		return (xsdNamesToOveride.get( schemaAddress));
	}
}
