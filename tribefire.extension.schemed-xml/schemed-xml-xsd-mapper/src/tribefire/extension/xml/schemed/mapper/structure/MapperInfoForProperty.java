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
package tribefire.extension.xml.schemed.mapper.structure;

import com.braintribe.model.meta.GmProperty;

import tribefire.extension.xml.schemed.mapping.metadata.PropertyMappingMetaData;

/**
 * info for a {@link GmProperty}
 * @author pit
 *
 */
public class MapperInfoForProperty {
	private GmProperty property;
	private PropertyMappingMetaData metaData;

	public PropertyMappingMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(PropertyMappingMetaData metaData) {
		this.metaData = metaData;
	}

	public GmProperty getProperty() {
		return property;
	}
	public void setProperty(GmProperty property) {
		this.property = property;
	}
	
	
		
}
