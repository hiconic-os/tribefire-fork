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

import java.util.StringJoiner;

import com.braintribe.common.lcd.UnsupportedEnumException;
import com.braintribe.model.access.sql.dialect.SqlDialect;
import com.braintribe.model.dbs.DbColumn;
import com.braintribe.model.dbs.DbTable;
import com.braintribe.model.generic.reflection.GenericModelType;

/**
 * @author peter.gazdik
 */
public interface RdbmsBase {

	static String getDataTypeName(GenericModelType type, SqlDialect sqlDialect) {
		switch (type.getTypeCode()) {
			case booleanType:
				return sqlDialect.booleanType();
			case dateType:
				return sqlDialect.dateType();
			case decimalType:
				return sqlDialect.decimalType();
			case doubleType:
				return sqlDialect.doubleType();
			case floatType:
				return sqlDialect.floatType();
			case integerType:
				return sqlDialect.integerType();
			case longType:
				return sqlDialect.longType();
			case stringType:
				return sqlDialect.stringType();
			case enumType:
				return sqlDialect.enumType();
			case entityType:
				return sqlDialect.entityType();
			case listType:
			case setType:
			case mapType:
				throw new IllegalArgumentException("Collection itself has no type. Given collection type:" + type.getTypeSignature());
			default:
				throw new UnsupportedEnumException(type.getTypeCode());
		}
	}

	static String computeHash(DbTable table) {
		// TODO use better hash?
		StringJoiner sj = new StringJoiner("#");

		for (DbColumn dbColumn : table.getColumns()) {
			sj.add(dbColumn.getName());
			sj.add(dbColumn.getDataTypeName());
		}

		return sj.toString();
	}

	// TODO maybe use db schema (learn what exactly it actually is, if it is needed to create one and so on...)
	static String qualifiedTableName(@SuppressWarnings("unused") /* TODO think about this!!! */ String dbOrNamespace, String tableName) {
		return tableName;
	}

}
