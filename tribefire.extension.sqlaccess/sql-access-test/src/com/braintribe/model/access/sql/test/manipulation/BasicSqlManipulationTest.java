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
package com.braintribe.model.access.sql.test.manipulation;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.access.sql.test.model.basic.BasicColor;
import com.braintribe.model.access.sql.test.model.basic.BasicEntity;
import com.braintribe.model.access.sql.test.model.basic.BasicScalarEntity;
import com.braintribe.testing.category.KnownIssue;

/**
 * @author peter.gazdik
 */
@Category(KnownIssue.class)
public class BasicSqlManipulationTest extends AbstractSqlAccessManipulationTest {

	@Test
	public void testCreateEntity() throws Exception {
		manipulationElf.apply(session -> {
			session.create(BasicScalarEntity.T);
		});
		
		// TODO make tableName retrievable via higher-level API
		assertNumberOfRows("BasicScalarEntity", 1);
	}
	

	@Test
	public void testCreateTwoEntities() throws Exception {
		manipulationElf.apply(session -> {
			session.create(BasicScalarEntity.T);
			session.create(BasicScalarEntity.T);
		});

		assertNumberOfRows("BasicScalarEntity", 2);
	}
	
	@Test
	public void testCreateEntityAndSetSimpleProperties() throws Exception {
		manipulationElf.apply(session -> {
			BasicScalarEntity e = session.create(BasicScalarEntity.T);
			e.setStringValue("String");
			e.setBooleanValue(true);
			e.setIntegerValue(111);
			e.setLongValue(222L);
			e.setFloatValue(333F);
			e.setDoubleValue(444D);
			e.setDateValue(new Date());
			e.setDecimalValue(new BigDecimal("11223344556677889900"));
			e.setColor(BasicColor.green);
		});
		
		// TODO make tableName retrievable via higher-level API
		assertNumberOfRows("BasicScalarEntity", 1);
		// TODO extend asserts
	}
	
	@Test
	public void testMix() throws Exception {
		manipulationElf.apply(session -> {
			BasicScalarEntity e = session.create(BasicScalarEntity.T);
			e.setStringValue("String");
			e.setBooleanValue(true);
			e.setIntegerValue(111);
			e.setLongValue(222L);
			e.setFloatValue(333F);
			e.setDoubleValue(444D);
			e.setDateValue(new Date());
			e.setDecimalValue(new BigDecimal("11223344556677889900.13456789"));
			e.setColor(BasicColor.green);
			
			BasicEntity yin = session.create(BasicEntity.T);
			yin.setId("yin");
			yin.setName("Yin");

			BasicEntity yang = session.create(BasicEntity.T);
			yang.setId("yang");
			yang.setName("Yang");
			
			yin.setBasicEntity(yang);
			yang.setBasicEntity(yin);
		});
	}
	
	
}
