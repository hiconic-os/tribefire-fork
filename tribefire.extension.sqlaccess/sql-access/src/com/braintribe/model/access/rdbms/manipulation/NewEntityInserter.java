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
package com.braintribe.model.access.rdbms.manipulation;

import static com.braintribe.model.access.sql.tools.JdbcTools.tryDoPreparedStatement;
import static com.braintribe.utils.SysPrint.spOut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.braintribe.model.access.rdbms.context.RdbmsManipulationContext;
import com.braintribe.model.access.sql.tools.SqlMappingExpert;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.BaseType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;

/**
 * @author peter.gazdik
 */
public class NewEntityInserter {

	private final RdbmsManipulationContext context;
	private final EntityType<?> entityType;
	private final SqlMappingExpert sqlMappingExpert;

	private String sql;

	public NewEntityInserter(EntityType<?> entityType, RdbmsManipulationContext context) {
		this.entityType = entityType;
		this.context = context;
		sqlMappingExpert = context.getSqlMappingExpert();

		initialize();
	}

	private void initialize() {
		List<Property> directValueProperties = context.getDirectvaluePropertiesFor(entityType);

		String tableName = sqlMappingExpert.resolveDbTableName(entityType);
		String joinedColumnNames = directValueProperties.stream().map(p -> sqlMappingExpert.resolveDbColumnName(entityType, p))
				.collect(Collectors.joining(","));
		String valuesWildcards = String.join(",", Collections.nCopies(directValueProperties.size(), "?"));

		sql = context.getSqlDialect().insertIntoPsTemplate(tableName, joinedColumnNames, valuesWildcards);
	}

	public void doBulkInsert(Connection c, List<GenericEntity> newEntities) throws Exception {
		tryDoPreparedStatement(c, sql, ps -> insertEntities(ps, newEntities));
	}

	private void insertEntities(PreparedStatement ps, List<GenericEntity> newEntities) throws SQLException {
		for (GenericEntity newEntity : newEntities)
			insertEntity(ps, newEntity);
	}

	private void insertEntity(PreparedStatement ps, GenericEntity newEntity) throws SQLException {
		List<Property> directValueProperties = context.getDirectvaluePropertiesFor(entityType);

		int i = 1;
		for (Property p : directValueProperties)
			ps.setObject(i++, getPropertyValue(newEntity, p));

		spOut("Adding new entity: " + sql);

		ps.executeUpdate();
		// TODO log trace
	}

	private Object getPropertyValue(GenericEntity newEntity, Property p) {
		Object value = newEntity.read(p);

		if (value == null)
			return null;

		GenericModelType propertyType = p.getType();
		if (propertyType == BaseType.INSTANCE) {
			propertyType = GMF.getTypeReflection().getType(value);
		}

		switch (propertyType.getTypeCode()) {
			case enumType:
				return value.toString();
			case entityType:
				return ((GenericEntity) value).getId();
			default:
				return value;
		}
	}

}
