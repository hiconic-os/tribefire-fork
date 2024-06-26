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
package com.braintribe.model.access.rdbms.deploy;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.access.sql.dialect.SqlDialect;
import com.braintribe.model.access.sql.dialect.StandardSqls;
import com.braintribe.model.access.sql.tools.SqlMappingExpert;
import com.braintribe.model.dbs.DbColumn;
import com.braintribe.model.dbs.DbSchema;
import com.braintribe.model.dbs.DbTable;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EssentialTypes;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.oracle.ModelOracle;

/**
 * @author peter.gazdik
 */
public class RdbmsDbSchemaProvider {

	/**
	 * These properties are set in the result:
	 * 
	 * {@code
	 * DbSchema.tables
	 * 
	 * DbTable.name (EntityType.getShortName)
	 * DbTable.columns (sorted by name)
	 * 
	 * DbColumn.owner
	 * DbColumn.name = Property.getName
	 * DbColumn.dataTypename = type name that can be used in RDBMS (like 'timestamp' or 'datetime' for date) 
	 * }
	 * 
	 * @see RdbmsBase#getDataTypeName
	 * @see StandardSqls
	 */
	public static DbSchema provide(CmdResolver cmdResolver, SqlDialect sqlDialect) {
		return new RdbmsDbSchemaProvider(cmdResolver, sqlDialect).provide();
	}

	private final ModelOracle modelOracle;
	private final SqlDialect sqlDialect;
	private final SqlMappingExpert sqlMappingExpert;

	private final Map<String, EntityType<?>> usedTableNames = newMap();
	private final Map<String, Property> usedColumnNames = newMap();

	private RdbmsDbSchemaProvider(CmdResolver cmdResolver, SqlDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
		this.modelOracle = cmdResolver.getModelOracle();
		this.sqlMappingExpert = new SqlMappingExpert(cmdResolver);
	}

	private DbSchema provide() {
		Set<EntityType<?>> instantiableTypes = getInstantiableEntityTypes();

		DbSchema result = DbSchema.T.create();
		Set<DbTable> tables = result.getTables();

		for (EntityType<?> entityType : instantiableTypes) {
			DbTable dbTable = provideDbTableFor(entityType);
			tables.add(dbTable);
		}

		return result;
	}

	private Set<EntityType<?>> getInstantiableEntityTypes() {
		return modelOracle.getEntityTypeOracle(GenericEntity.T) //
				.getSubTypes() //
				.transitive() //
				.onlyInstantiable() //
				.asTypes();
	}

	private DbTable provideDbTableFor(EntityType<?> entityType) {
		String dbTableName = resolveDbTableName(entityType);

		usedColumnNames.clear();

		DbTable result = DbTable.T.create();
		result.setName(dbTableName);

		List<DbColumn> columns = result.getColumns();

		for (Property property : entityType.getProperties()) {
			DbColumn dbColumn = provideDbColumnFor(result, entityType, property);
			if (dbColumn != null)
				columns.add(dbColumn);
		}

		return result;
	}

	private DbColumn provideDbColumnFor(DbTable owner, EntityType<?> entityType, Property property) {
		if (property.isGlobalId())
			return null;

		GenericModelType propertyType = property.isIdentifier() ? EssentialTypes.TYPE_STRING : property.getType();

		if (propertyType.isCollection() || propertyType.isBase())
			return null;

		DbColumn result = DbColumn.T.create();
		result.setOwner(owner);
		result.setName(resolveDbColumnName(entityType, property));
		result.setDataTypeName(RdbmsBase.getDataTypeName(propertyType, sqlDialect));

		return result;
	}

	private String resolveDbTableName(EntityType<?> entityType) {
		String result = sqlMappingExpert.resolveDbTableName(entityType);
		return ensureUnique(result, usedTableNames, entityType, "Table");
	}

	private String resolveDbColumnName(EntityType<?> entityType, Property property) {
		String result = sqlMappingExpert.resolveDbColumnName(entityType, property);
		return ensureUnique(result, usedColumnNames, property, "Column");
	}

	private <T> String ensureUnique(String name, Map<String, T> usedNames, T namedElement, String descriptor) {
		T previousElement = usedNames.put(name, namedElement);

		if (previousElement != null)
			throw new GenericModelException(
					descriptor + " name is not unique. The same name was resolved for '" + previousElement + "' and '" + namedElement + "'.");

		return name;
	}

}
