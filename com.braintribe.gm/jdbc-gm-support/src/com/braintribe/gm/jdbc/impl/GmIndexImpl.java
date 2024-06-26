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
package com.braintribe.gm.jdbc.impl;

import com.braintribe.gm.jdbc.api.GmColumn;
import com.braintribe.gm.jdbc.api.GmIndex;

/**
 * @author peter.gazdik
 */
public class GmIndexImpl implements GmIndex {

	private final String name;
	private final GmColumn<?> column;

	public GmIndexImpl(String name, GmColumn<?> column) {
		this.name = name;
		this.column = column;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public GmColumn<?> getColumn() {
		return column;
	}

}
