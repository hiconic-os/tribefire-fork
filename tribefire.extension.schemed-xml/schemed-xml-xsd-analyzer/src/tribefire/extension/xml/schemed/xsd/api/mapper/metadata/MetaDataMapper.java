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
package tribefire.extension.xml.schemed.xsd.api.mapper.metadata;

import java.util.Collection;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.data.HasMetaData;
import com.braintribe.model.meta.data.MetaData;

import tribefire.extension.xml.schemed.mapping.metadata.EntityTypeMappingMetaData;
import tribefire.extension.xml.schemed.mapping.metadata.EnumConstantMappingMetaData;
import tribefire.extension.xml.schemed.mapping.metadata.EnumTypeMappingMetaData;
import tribefire.extension.xml.schemed.mapping.metadata.ModelMappingMetaData;
import tribefire.extension.xml.schemed.mapping.metadata.PropertyMappingMetaData;

/**
 * attaches {@link MetaData}
 * @author pit
 *
 */
public interface MetaDataMapper {
	
	/**
	 * attach collected {@link MetaData} (constraints as collected through the drill-down of restriction/extensions)
	 * @param hasMetaData - the {@link HasMetaData} entity (GmProperty, GmEntityType, GmEnumType, GmEnumConstant in our case)
	 * @param constraints - a {@link Collection} of {@link MetaData} (constraints)
	 */
	void attachConstraints( HasMetaData hasMetaData, Collection<MetaData> constraints);


	/**
	 * acquire (and prime if required) the mapping meta data for a {@link GmEntityType}
	 * @param type - the {@link GmEntityType}
	 * @return - a primed {@link EntityTypeMappingMetaData}
	 */
	EntityTypeMappingMetaData acquireMetaData( GmEntityType type);
	
	/**
	 * acquire (and prime if required) the mapping meta data for a {@link GmEnumType}
	 * @param type - the {@link GmEnumType}
	 * @return - the {@link EnumTypeMappingMetaData}
	 */
	EnumTypeMappingMetaData acquireMetaData( GmEnumType type);
	/**
	 * acquire (and prime if required) the mapping meta data for a {@link GmEnumConstant}
	 * @param constant - the {@link GmEnumConstant}
	 * @return - the {@link EnumConstantMappingMetaData}
	 */
	EnumConstantMappingMetaData acquireMetaData( GmEnumConstant constant);
	/**
	 * acquire (and prime if required) the mapping meta data for a {@link GmProperty}
	 * @param property - the {@link GmProperty}
	 * @return - the {@link PropertyMappingMetaData}
	 */
	PropertyMappingMetaData acquireMetaData( GmProperty property);
	
	/**
	 * acquire (and prime if required) the mapping meta data for a {@link GmProperty}
	 * @param metaModel - {@link GmMetaModel} the metamodel to attach to 
	 * @return - the {@link ModelMappingMetaData}
	 */
	ModelMappingMetaData acquireMetaData( GmMetaModel metaModel);

}
