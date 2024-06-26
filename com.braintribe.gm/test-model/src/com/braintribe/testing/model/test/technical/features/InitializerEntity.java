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
package com.braintribe.testing.model.test.technical.features;

import java.math.BigDecimal;
import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * An entity with various properties with {@link Initializer}s, i.e. default values.
 *
 * @author michael.lafite
 */

public interface InitializerEntity extends GenericEntity {

	EntityType<InitializerEntity> T = EntityTypes.T(InitializerEntity.class);

	@Initializer("enum(com.braintribe.testing.model.test.technical.features.SimpleEnum,TWO)")
	SimpleEnum getEnumProperty();
	void setEnumProperty(SimpleEnum enumProperty);

	@Initializer("'abc'")
	String getStringProperty();
	void setStringProperty(String stringProperty);

	@Initializer("true")
	Boolean getBooleanProperty();
	void setBooleanProperty(Boolean booleanProperty);

	@Initializer("123")
	Integer getIntegerProperty();
	void setIntegerProperty(Integer integerProperty);

	@Initializer("123l")
	Long getLongProperty();
	void setLongProperty(Long longProperty);

	@Initializer("123.45f")
	Float getFloatProperty();
	void setFloatProperty(Float floatProperty);

	@Initializer("123.45d")
	Double getDoubleProperty();
	void setDoubleProperty(Double doubleProperty);

	@Initializer("123.45b")
	BigDecimal getDecimalProperty();
	void setDecimalProperty(BigDecimal decimalProperty);

	@Initializer("now()")
	Date getDateProperty();
	void setDateProperty(Date dateProperty);

	@Initializer("true")
	boolean getPrimitiveBooleanProperty();
	void setPrimitiveBooleanProperty(boolean primitiveBooleanProperty);

	@Initializer("123")
	int getPrimitiveIntegerProperty();
	void setPrimitiveIntegerProperty(int primitiveIntegerProperty);

	@Initializer("123l")
	long getPrimitiveLongProperty();
	void setPrimitiveLongProperty(long primitiveLongProperty);

	@Initializer("123.45f")
	float getPrimitiveFloatProperty();
	void setPrimitiveFloatProperty(float primitiveFloatProperty);

	@Initializer("123.45d")
	double getPrimitiveDoubleProperty();
	void setPrimitiveDoubleProperty(double primitiveDoubleProperty);
}
