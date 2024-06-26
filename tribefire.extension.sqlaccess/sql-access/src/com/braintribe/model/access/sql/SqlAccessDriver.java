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
package com.braintribe.model.access.sql;

import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;

/**
 * @author peter.gazdik
 */
public interface SqlAccessDriver {

	CmdResolver getCmdResolver();
	
	// TODO later only parts of querying will be in the driver
	SelectQueryResult query(SelectQuery query);
	
	ManipulationResponse applyManipulation(SqlManipulationReport localReport);
	
}
