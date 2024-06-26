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
package com.braintribe.model.access.sql.test.base.javax;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.braintribe.model.generic.reflection.GenericModelException;

/**
 * @author peter.gazdik
 */
public class NonCommittingDataSource extends DelegatingDataSource {

	private final NonCommittingConnection connection;
	
	public NonCommittingDataSource(DataSource delegate) {
		super(delegate);
		
		this.connection = newNonCommittingConnection();
	}

	private NonCommittingConnection newNonCommittingConnection()  {
		try {
			return new NonCommittingConnection(delegate.getConnection());
		} catch (SQLException e) {
			throw new GenericModelException("Failed to get connection.", e);
		}
	}
	
	@Override
	public NonCommittingConnection getConnection() {
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new UnsupportedOperationException("Method 'NonCommittingDataSource.getConnection' is not supported!");
	}

}
