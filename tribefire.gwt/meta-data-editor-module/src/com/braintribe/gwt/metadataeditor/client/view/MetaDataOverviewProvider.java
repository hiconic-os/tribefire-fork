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
package com.braintribe.gwt.metadataeditor.client.view;

import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;
import com.google.gwt.user.client.ui.IsWidget;

public interface MetaDataOverviewProvider extends IsWidget {
	
	public void addMapMetaDataForProperty(String key, EntityType<? extends MetaData> value);
	
	public void addMapMetaDataForEntityType(String key, EntityType<? extends MetaData> value);

	public void removeMapMetaDataForProperty(String key);
	
	public void removeMapMetaDataForEntityType(String key);
	
	public void addAllMapMetaDataForProperty(Map<String, EntityType<? extends MetaData>> value);
	
	public void addAllMapMetaDataForEntityType(Map<String, EntityType<? extends MetaData>> value);
	
	public void removeAllMapMetaDataForProperty();
	
	public void removeAllMapMetaDataForEntityType();
	
	public void setAllowType(List<Class<? extends GenericEntity>> listClass);
	
	public List<Class<? extends GenericEntity>> getAllowType();

	public void addMapMetaDataForEnumConstant(String key, EntityType<? extends MetaData> value);

	public void removeMapMetaDataForEnumConstant(String key);

	public void removeAllMapMetaDataForEnumConstant();

	public void addAllMapMetaDataForEnumConstant(Map<String, EntityType<? extends MetaData>> value);	
	
}
