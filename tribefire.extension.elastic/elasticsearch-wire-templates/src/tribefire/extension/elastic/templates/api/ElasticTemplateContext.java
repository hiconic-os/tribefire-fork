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
package tribefire.extension.elastic.templates.api;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.deployment.Cartridge;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.wire.api.scope.InstanceConfiguration;

public interface ElasticTemplateContext {

	String getName();

	String getClusterName();

	String getIdPrefix();

	String getIndex();

	boolean getClusterSniff();

	String getConnectorHost();

	int getConnectorPort();

	int getHttpPort();

	boolean getDeployConnector();

	int getMaxFulltextResultSize();

	int getMaxResultWindow();

	Cartridge getElasticCartridge();

	com.braintribe.model.deployment.Module getElasticModule();

	IncrementalAccess getAccess();

	int getIndexingThreadCount();

	int getIndexingQueueSize();

	<T extends GenericEntity> T lookup(String globalId);

	static ElasticTemplateContextBuilder builder() {
		return new ElasticTemplateContextImpl();
	}

	<T extends GenericEntity> T create(EntityType<T> entityType, InstanceConfiguration instanceConfiguration);
}