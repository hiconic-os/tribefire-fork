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
package com.braintribe.util.jdbc.pagination.dialect;

import com.braintribe.util.jdbc.pagination.AbstractLimitHandler;
import com.braintribe.util.jdbc.pagination.LimitHelper;
import com.braintribe.util.jdbc.pagination.RowSelection;

public class MySqlLimitHandler extends AbstractLimitHandler {

	public static final MySqlLimitHandler INSTANCE = new MySqlLimitHandler();

	@Override
	public String processSql(String sql, RowSelection selection) {
		final boolean hasOffset = LimitHelper.hasFirstRow( selection );
		return sql + (hasOffset ? " limit ?, ?" : " limit ?");
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

}
