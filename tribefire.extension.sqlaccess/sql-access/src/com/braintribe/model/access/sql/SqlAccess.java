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

import com.braintribe.cfg.Required;
import com.braintribe.model.access.AccessBase;
import com.braintribe.model.access.ModelAccessException;
import com.braintribe.model.access.sql.manipulation.SqlManipulationApplicator;
import com.braintribe.model.access.sql.manipulation.SqlManipulationReportImpl;
import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.oracle.ModelOracle;
import com.braintribe.model.processing.query.support.QueryAdaptingTools;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.PropertyQueryResult;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;

/**
 * @author peter.gazdik
 */
public class SqlAccess extends AccessBase {

	private GmMetaModel metaModel;
	private CmdResolver cmdResolver;
	private ModelOracle modelOracle;
	private SqlAccessDriver sqlAccessDriver;

	@Required
	public void setSqlAccessDriver(SqlAccessDriver sqlAccessDriver) {
		this.sqlAccessDriver = sqlAccessDriver;
		this.cmdResolver = sqlAccessDriver.getCmdResolver();
		this.modelOracle = cmdResolver.getModelOracle();
		this.metaModel = modelOracle.getGmMetaModel();
	}

	@Override
	public SelectQueryResult query(SelectQuery query) throws ModelAccessException {
		return sqlAccessDriver.query(query);
	}

	@Override
	public EntityQueryResult queryEntities(EntityQuery entityQuery) throws ModelAccessException {
		return QueryAdaptingTools.queryEntities(entityQuery, this);
	}

	@Override
	public PropertyQueryResult queryProperty(PropertyQuery propertyQuery) throws ModelAccessException {
		return QueryAdaptingTools.queryProperties(propertyQuery, this);
	}

	@Override
	public ManipulationResponse applyManipulation(ManipulationRequest manipulationRequest) throws ModelAccessException {
		SqlManipulationReportImpl report = SqlManipulationApplicator.appy(this, manipulationRequest.getManipulation());

		return sqlAccessDriver.applyManipulation(report);		
	}

	@Override
	public GmMetaModel getMetaModel() throws GenericModelException {
		return metaModel;
	}

}
