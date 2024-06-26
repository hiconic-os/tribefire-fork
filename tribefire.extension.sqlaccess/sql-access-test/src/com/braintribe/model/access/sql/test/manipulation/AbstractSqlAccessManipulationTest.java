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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;

import com.braintribe.model.access.sql.test.base.AbstractSqlAccessTest;
import com.braintribe.model.access.sql.test.base.ManipulationElf;
import com.braintribe.model.access.sql.tools.JdbcTools;

/**
 * @author peter.gazdik
 */
public abstract class AbstractSqlAccessManipulationTest extends AbstractSqlAccessTest {

	protected ManipulationElf manipulationElf;

	@Before
	public void beforeManipulationTest() throws Exception {
		manipulationElf = getManipulationElf();
	}

	protected ManipulationElf getManipulationElf() {
		return new ManipulationElf(access);
	}

	protected void assertNumberOfRows(String tableName, int count) {
		JdbcTools.doStatement(dataSource, s -> assertNumberOfRows(s, tableName, count));
	}

	private void assertNumberOfRows(Statement s, String tableName, int expectedCount) throws SQLException {
		String query = String.format("SELECT COUNT(*) FROM %s", tableName);

		try (ResultSet rs = s.executeQuery(query)) {
			rs.next();
			int actualCount = rs.getInt(1);
			assertThat(actualCount).isEqualTo(expectedCount);
		}
	}

}
