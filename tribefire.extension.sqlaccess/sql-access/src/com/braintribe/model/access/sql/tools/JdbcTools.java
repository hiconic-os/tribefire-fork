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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import com.braintribe.model.access.sql.api.ThrowingConsumer;
import com.braintribe.model.access.sql.api.ThrowingFunction;

/**
 * @author peter.gazdik
 */
public class JdbcTools {

	public static void doStatement(DataSource dataSource, ThrowingConsumer<Statement> consumer) {
		try {
			tryDoStatement(dataSource, consumer);

		} catch (Exception e) {
			throw new RuntimeException("Error while executing SQL statement(s).", e);
		}
	}

	public static void tryDoStatement(DataSource dataSource, ThrowingConsumer<Statement> consumer) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			tryDoStatement(connection, consumer);
		}
	}

	public static void tryDoStatement(Connection connection, ThrowingConsumer<Statement> consumer) throws Exception {
		try (Statement statement = connection.createStatement()) {
			consumer.accept(statement);
		}
	}

	public static <R> R tryComputeStatement(DataSource dataSource, ThrowingFunction<Statement, R> consumer) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				return consumer.apply(statement);
			}
		}
	}

	public static void tryDoPreparedStatement(Connection connection, String sql, ThrowingConsumer<PreparedStatement> consumer) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			consumer.accept(preparedStatement);
		}
	}

	public static <R> R tryComputePreparedStatement(Connection connection, String sql, ThrowingFunction<PreparedStatement, R> consumer)
			throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			return consumer.apply(preparedStatement);
		}
	}

}
