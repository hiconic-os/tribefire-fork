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
package com.braintribe.model.processing.dbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.function.Function;

import javax.sql.DataSource;

import com.braintribe.logging.Logger;
import com.braintribe.model.dbs.DbSchema;

/**
 * Provides {@link DbSchema} for given {@link DataSource} using the standard
 * jdbc API for retrieving DB meta data.
 */
public class DbSchemaProvider implements Function<DataSource, Set<DbSchema>> {

	private static String SCHEMA = null;

	Logger log = Logger.getLogger(DbSchemaProvider.class);

	@Override
	public Set<DbSchema> apply(DataSource dataSource) throws RuntimeException {
		try {
			Connection connection = dataSource.getConnection();
			try {
				return apply(connection);

			} finally {
				connection.close();
			}

		} catch (SQLException e) {
			throw new RuntimeException("Failed to retrieve DbSchema information.", e);
		}
	}

	public Set<DbSchema> apply(Connection connection) throws SQLException {
		return new SchemaProviderHelper(connection.getMetaData(), connection.getCatalog(), getSchema(connection))
				.provide();
	}

	private String getSchema(Connection connection) {
		try {
			return connection.getSchema();

		} catch (SQLException ignored) {
			log.debug(
					"JDBC driver throwed an exception. This is expected to happen also on successful sync, so continuing: ",
					ignored);
			/*
			 * jdbc driver might throw SQLFeatureNotSupportedException (at least SQL Server
			 * driver does that)
			 */

		} catch (LinkageError ignored) {
			log.debug(
					"The Connection.getSchema() method was introduces with java 7, so older drivers might not have that method at all, causing: ",
					ignored);
			/*
			 * the Connection.getSchema() method was introduces with java 7, so older
			 * drivers might not have that method at all, causing AbstractMethodError (could
			 * it ever be NoSuchMethodError?). Just to be safe, let's catch a LinkageError
			 * here.
			 */
		}

		return SCHEMA;
	}

}
