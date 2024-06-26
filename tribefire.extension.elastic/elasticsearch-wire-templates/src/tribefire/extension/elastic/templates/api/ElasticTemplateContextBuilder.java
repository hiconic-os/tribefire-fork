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

import java.util.function.Function;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.deployment.Cartridge;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface ElasticTemplateContextBuilder {

	ElasticTemplateContextBuilder setName(String name);

	ElasticTemplateContextBuilder setClusterName(String clusterName);

	ElasticTemplateContextBuilder setClusterSniff(boolean clusterSniff);

	ElasticTemplateContextBuilder setConnectorHost(String connectorHost);

	ElasticTemplateContextBuilder setConnectorPort(int connectorPort);

	ElasticTemplateContextBuilder setHttpPort(int httpPort);

	ElasticTemplateContextBuilder setDeployConnector(boolean deployConnector);

	ElasticTemplateContextBuilder setIdPrefix(String idPrefix);

	ElasticTemplateContextBuilder setElasticCartridge(Cartridge elasticCartridge);

	ElasticTemplateContextBuilder setElasticModule(com.braintribe.model.deployment.Module elasticModule);

	ElasticTemplateContextBuilder setIndex(String index);

	ElasticTemplateContextBuilder setMaxFulltextResultSize(int maxFulltextResultSize);

	ElasticTemplateContextBuilder setMaxResultWindow(int maxResultWindow);

	ElasticTemplateContextBuilder setIndexingThreadCount(int indexingThreadCount);

	ElasticTemplateContextBuilder setIndexingQueueSize(int indexingQueueSize);

	ElasticTemplateContextBuilder setAccess(IncrementalAccess access);

	ElasticTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory);

	ElasticTemplateContextBuilder setLookupFunction(Function<String, ? extends GenericEntity> lookupFunction);

	ElasticTemplateContext build();

}