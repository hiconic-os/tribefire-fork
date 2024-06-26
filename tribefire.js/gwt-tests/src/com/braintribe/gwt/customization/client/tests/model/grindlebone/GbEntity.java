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
package com.braintribe.gwt.customization.client.tests.model.grindlebone;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


public interface GbEntity extends GenericEntity {
	
	final EntityType<GbEntity> T = EntityTypes.T(GbEntity.class);
	
	Integer getIntegerWrapper();
	void setIntegerWrapper(Integer value);
	
	Long getLongWrapper();
	void setLongWrapper(Long value);
	
	Double getDoubleWrapper();
	void setDoubleWrapper(Double value);
	
	Float getFloatWrapper();
	void setFloatWrapper(Float value);
	
	Boolean getBooleanWrapper();
	void setBooleanWrapper(Boolean value);
	
	boolean getBooleanValue();
	void setBooleanValue(boolean value);
	
	int getIntegerValue();
	void setIntegerValue(int value);
	
	long getLongValue();
	void setLongValue(long value);
	
	double getDoubleValue();
	void setDoubleValue(double value);
	
	float getFloatValue();
	void setFloatValue(float value);
	
	String getStringValue();
	void setStringValue(String value);
}
