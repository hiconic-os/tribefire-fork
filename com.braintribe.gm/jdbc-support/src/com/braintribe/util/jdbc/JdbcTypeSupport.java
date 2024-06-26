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
package com.braintribe.util.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.util.jdbc.dialect.JdbcDialect;
import com.braintribe.utils.IOTools;

public class JdbcTypeSupport {

	private static final Logger logger = Logger.getLogger(JdbcTypeSupport.class);

	protected static String getDatabaseSpecificType(String dbName, String typeName, String defaultValue) {
		String key = "TRIBEFIRE_JDBC_TYPE_" + typeName.toUpperCase() + "_" + dbName.toUpperCase().replace(' ', '_');
		String type = TribefireRuntime.getProperty(key);
		logger.debug(
				() -> "Specific mapping for type " + typeName + " with " + dbName + " (" + key + "): " + type + " (default: " + defaultValue + ")");
		if (type == null) {
			return defaultValue;
		}
		return type;
	}

	public static DatabaseTypes getDatabaseTypes(DataSource dataSource) {

		final String dbName = getDatabaseProductName(dataSource);
		logger.debug(() -> "Identified database: " + dbName);

		String clobType = "CLOB";
		if (dbName != null) {
			final String lowerCaseDbName = dbName.toLowerCase();
			if ((lowerCaseDbName.contains("mysql") || (lowerCaseDbName.contains("mariadb")))) {
				clobType = "LONGTEXT";
			} else if (lowerCaseDbName.contains("postgresql") || (lowerCaseDbName.contains("microsoft sql"))) {
				clobType = "TEXT";
			}
			clobType = getDatabaseSpecificType(dbName, "CLOB", clobType);
		}

		String blobType = "BLOB";
		if (dbName != null) {
			final String lowerCaseDbName = dbName.toLowerCase();
			if ((lowerCaseDbName.contains("mysql") || (lowerCaseDbName.contains("mariadb")))) {
				blobType = "MEDIUMBLOB";
			} else if (lowerCaseDbName.contains("postgresql")) {
				blobType = "oid";
			} else if (lowerCaseDbName.contains("microsoft sql")) {
				blobType = "IMAGE";
			}
			blobType = getDatabaseSpecificType(dbName, "BLOB", blobType);
		}

		String timestampType = "TIMESTAMP";
		if (dbName != null) {
			final String lowerCaseDbName = dbName.toLowerCase();
			if (lowerCaseDbName.contains("microsoft sql")) {
				timestampType = "DATETIME2";
			}
			timestampType = getDatabaseSpecificType(dbName, "TIMESTAMP", timestampType);
		}

		DatabaseTypes types = new DatabaseTypes(dbName, clobType, blobType, timestampType);
		logger.debug(() -> types.toString());

		return types;
	}

	public static JdbcDialect getJdbcDialect(DataSource dataSource) {
		return JdbcDialect.detectDialect(dataSource);
	}

	public static String getDatabaseProductName(DataSource dataSource) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			return connection.getMetaData().getDatabaseProductName();
		} catch (Exception e) {
			throw new RuntimeException("Could not get information about the database.", e);
		} finally {
			IOTools.closeCloseable(connection, logger);
		}
	}
}
