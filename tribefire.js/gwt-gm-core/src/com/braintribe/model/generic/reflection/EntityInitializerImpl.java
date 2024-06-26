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
package com.braintribe.model.generic.reflection;

import java.util.Date;

import com.braintribe.model.bvd.time.Now;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.EnumReference;

/**
 * @author peter.gazdik
 */
public abstract class EntityInitializerImpl implements EntityInitializer {

	protected Property property;

	@Override
	public abstract void initialize(GenericEntity entity);

	public static EntityInitializer newInstance(Property property) {
		return newInstance(property, property.getInitializer());
	}

	public static EntityInitializer newInstance(Property property, Object initializer) {
		EntityInitializerImpl result = newInitializerFor(initializer);
		result.property = property;
		
		return result;
	}

	private static EntityInitializerImpl newInitializerFor(Object initializer) {
		if (initializer instanceof GenericEntity) {
			if (initializer instanceof Now) {
				return new CurrentDateInitializer();
			}

			if (initializer instanceof EnumReference) {
				Object enumValue = resolveEnumConstant((EnumReference) initializer);
				return new StaticInitializer(enumValue);
			}
			
			throw new RuntimeException("No initializer implementation exists for initializer: " + initializer);
		}

		return new StaticInitializer(initializer);
	}

	private static Object resolveEnumConstant(EnumReference enumReference) {
		EnumType et = GMF.getTypeReflection().getType(enumReference.getTypeSignature());
		return et.getEnumValue(enumReference.getConstant());
	}
	
	static class StaticInitializer extends EntityInitializerImpl {
		private final Object value;

		public StaticInitializer(Object value) {
			this.value = value;
		}

		@Override
		public void initialize(GenericEntity entity) {
			property.set(entity, value);
		}
	}

	static class CurrentDateInitializer extends EntityInitializerImpl {
		@Override
		public void initialize(GenericEntity entity) {
			property.set(entity, new Date());
		}
	}
}
