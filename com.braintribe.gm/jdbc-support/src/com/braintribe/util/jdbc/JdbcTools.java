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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.sql.DataSource;

import com.braintribe.common.lcd.function.XConsumer;
import com.braintribe.util.jdbc.dialect.JdbcDialect;
import com.braintribe.util.jdbc.pagination.LimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.DB2LimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.DerbyLimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.MySqlLimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.Oracle9iLimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.PostgreSqlLimitHandler;
import com.braintribe.util.jdbc.pagination.dialect.SQLServer2012LimitHandler;
import com.braintribe.utils.StringTools;

/**
 * @author peter.gazdik
 */
public class JdbcTools {

	/** Executes given task with a new {@link Connection} whose {@link Connection#getAutoCommit()} returns false. */
	public static void withManualCommitConnection(DataSource dataSource, Supplier<String> details, XConsumer<Connection> task) {
		JdbcToolsImplementation.withManualCommitConnection(dataSource, details, task);
	}

	/**
	 * Executes given task with a new {@link Connection} keeping the {@link Connection#getAutoCommit()} flag untouched. The connection is committed
	 * afterwards if <tt>commit</tt> parameter is true.
	 */
	public static void withConnection(DataSource dataSource, boolean commit, Supplier<String> details, XConsumer<Connection> task) {
		JdbcToolsImplementation.withConnection(dataSource, commit, details, task);
	}

	/**
	 * Creates a new statement on a new manual-commit connection, runs given task against it and commits. Shortcut for:
	 * 
	 * <code>
	 * 		withManualCommitConnection(dataSource, details, c -> {
	 *			withStatement(c, details, task);
	 *		});
	 *</code>
	 */
	public static void withStatement(DataSource dataSource, Supplier<String> details, XConsumer<Statement> task) {
		withManualCommitConnection(dataSource, details, c -> {
			withStatement(c, details, task);
		});
	}

	public static void withStatement(Connection connection, Supplier<String> details, XConsumer<Statement> task) {
		JdbcToolsImplementation.withStatement(connection, details, task);
	}

	public static void withPreparedStatement(Connection connection, SqlStatement st, Supplier<String> details, XConsumer<PreparedStatement> task) {
		withPreparedStatement(connection, st.sql, st.parameters, details, task);
	}

	public static void withPreparedStatement(Connection connection, String sql, Supplier<String> details, XConsumer<PreparedStatement> task) {
		withPreparedStatement(connection, sql, null, details, task);
	}

	/** Note the <tt>params</tt> are only here for logging purposes, they aren't bound to the preparedStatement given to the <tt>task</tt>. */
	public static void withPreparedStatement(Connection c, String sql, List<?> params, Supplier<String> details, XConsumer<PreparedStatement> task) {
		JdbcToolsImplementation.withPreparedStatement(c, sql, params, details, task);
	}

	/** Attempts to commit or rollback and close given connection if it is not null and not {@link Connection#isClosed() closed} */
	public static void thoroughlyCloseJdbcConnection(Connection connection, boolean commit, Supplier<String> details) {
		JdbcToolsImplementation.thoroughlyCloseJdbcConnection(connection, commit, details);
	}

	/** Attempts to commit and close given connection if it is not null and not {@link Connection#isClosed() closed} */
	public static void commitAndCloseJdbcConnection(Connection connection, Supplier<String> details) {
		JdbcToolsImplementation.thoroughlyCloseJdbcConnection(connection, true, details);
	}

	/** Attempts to rollback and close given connection if it is not null and not {@link Connection#isClosed() closed} */
	public static void rollbackAndCloseJdbcConnection(Connection connection, Supplier<String> details) {
		JdbcToolsImplementation.thoroughlyCloseJdbcConnection(connection, false, details);
	}

	/**
	 * Checks if a table with given name exists. The table name comparison is case insensitive (i.e. "name" and "NAME" denote the same table.)
	 * 
	 * @return name of given table as found in the database (might differ from given table name in case), or null if no such table exists.
	 */
	public static String tableExists(Connection connection, String tableName) {
		return JdbcToolsImplementation.tableExists(connection, tableName);
	}

	/**
	 * Checks if given columns exist in given table. The table name comparison is case sensitive, i.e. must be provided precisely, i.e. case
	 * sensitively. The correct name can be resolved via {@link #tableExists(Connection, String)}.
	 */
	public static Set<String> columnsExist(Connection connection, String table, String... columns) {
		return JdbcToolsImplementation.columnsExist(connection, table, Arrays.asList(columns));
	}

	public static Set<String> columnsExist(Connection connection, String table, Collection<String> columns) {
		return JdbcToolsImplementation.columnsExist(connection, table, columns);
	}

	/**
	 * Checks if given indices exist in given table. The table name comparison is case sensitive, i.e. must be provided precisely, i.e. case
	 * sensitively. The correct name can be resolved via {@link #tableExists(Connection, String)}.
	 */
	public static Set<String> indicesExist(Connection connection, String table, String... indices) {
		return JdbcToolsImplementation.indicesExist(connection, table, Arrays.asList(indices));
	}

	public static Set<String> indicesExist(Connection connection, String table, Collection<String> indices) {
		return JdbcToolsImplementation.indicesExist(connection, table, indices);
	}

	public static LimitHandler getLimitHandler(JdbcDialect dialect) {
		// If needed, we can make this more granular and support older versions of MS SQL and Oracle
		switch (dialect.knownDbVariant()) {
			case DB2:
			case DB2v7_Host:
				return DB2LimitHandler.INSTANCE;

			case derby:
				return DerbyLimitHandler.INSTANCE;

			case mssql:
				return SQLServer2012LimitHandler.INSTANCE;

			case mysql:
				return MySqlLimitHandler.INSTANCE;

			case oracle:
				return Oracle9iLimitHandler.INSTANCE;

			case postgre:
				return PostgreSqlLimitHandler.INSTANCE;

			default:
				throw new IllegalArgumentException("Pagination is not supported for JDBC dialect variant: " + dialect.dbVariant()
						+ ", which is based on hibernated dialect: " + dialect.hibernateDialect());
		}
	}

	public static String questionMarks(int times) {
		return " (" + StringTools.repeat("?,", times).substring(0, 2 * times - 1) + ")";
	}

}
