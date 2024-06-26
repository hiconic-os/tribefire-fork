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
package com.braintribe.model.access.sql.tools;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.meta.cmd.CmdResolver;

import tribefire.extension.sqlaccess.model.meta.SqlColumnName;
import tribefire.extension.sqlaccess.model.meta.SqlTableName;

/**
 * @author peter.gazdik
 */
public class SqlMappingExpert {

	private final CmdResolver cmdResolver;

	public SqlMappingExpert(CmdResolver cmdResolver) {
		this.cmdResolver = cmdResolver;
	}

	public String resolveDbTableName(EntityType<?> entityType) {
		SqlTableName sqlTableName = cmdResolver.getMetaData().entityType(entityType).meta(SqlTableName.T).exclusive();
		return sqlTableName != null ? sqlTableName.getName() : entityType.getShortName();
	}

	public String resolveDbColumnName(EntityType<?> entityType, Property property) {
		SqlColumnName sqlColumnName = cmdResolver.getMetaData().entityType(entityType).property(property).meta(SqlColumnName.T).exclusive();
		return sqlColumnName != null ? sqlColumnName.getName() : property.getName();
	}

}
