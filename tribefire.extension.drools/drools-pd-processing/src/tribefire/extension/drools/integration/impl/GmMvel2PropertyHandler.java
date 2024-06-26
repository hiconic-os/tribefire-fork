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
package tribefire.extension.drools.integration.impl;

import org.mvel2.integration.PropertyHandler;
import org.mvel2.integration.PropertyHandlerFactory;
import org.mvel2.integration.VariableResolverFactory;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.Property;

public class GmMvel2PropertyHandler implements PropertyHandler {
	
	private static final GmMvel2PropertyHandler INSTANCE = new GmMvel2PropertyHandler();
	
	private Property property(GenericEntity entity, String name) {
		return entity.entityType().getProperty(name);
	}

	@Override
	public Object setProperty(String name, Object contextObj, VariableResolverFactory variableFactory, Object value) {
		GenericEntity entity = (GenericEntity) contextObj;
		Property property = property(entity, name);
		Object retVal = property.get(entity);
		property.set(entity, value);
		return retVal;
	}

	@Override
	public Object getProperty(String name, Object contextObj, VariableResolverFactory variableFactory) {
		GenericEntity entity = (GenericEntity) contextObj;
		Property property = property(entity, name);
		return property.get(entity);
	}
	
	public static void install() {
		PropertyHandlerFactory.registerPropertyHandler(GenericEntity.class, INSTANCE);
	}
}
