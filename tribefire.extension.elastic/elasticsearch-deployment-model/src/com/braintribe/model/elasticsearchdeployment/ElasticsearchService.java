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
package com.braintribe.model.elasticsearchdeployment;

import java.util.Set;

import com.braintribe.model.extensiondeployment.WebTerminal;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ElasticsearchService extends WebTerminal {

	final EntityType<ElasticsearchService> T = EntityTypes.T(ElasticsearchService.class);

	void setBasePath(String basePath);
	@Initializer("'WEB-INF/res'")
	String getBasePath();

	void setDataPath(String dataPath);
	String getDataPath();

	void setClusterName(String clusterName);
	@Initializer("'elasticsearch.cartridge'")
	String getClusterName();

	void setPluginClasses(Set<String> pluginClasses);
	@Initializer("{'org.elasticsearch.ingest.attachment.IngestAttachmentPlugin'}")
	Set<String> getPluginClasses();

	void setBindHosts(Set<String> bindHosts);
	Set<String> getBindHosts();

	void setPublishHost(String publishHost);
	String getPublishHost();

	void setPort(Integer port);
	@Initializer("9300")
	Integer getPort();

	void setHttpPort(Integer port);
	@Initializer("9200")
	Integer getHttpPort();

	void setRepositoryPaths(Set<String> repositoryPaths);
	Set<String> getRepositoryPaths();

	void setRecoverAfterNodes(Integer recoverAfterNodes);
	Integer getRecoverAfterNodes();

	void setExpectedNodes(Integer expectedNodes);
	Integer getExpectedNodes();

	void setRecoverAfterTimeInS(Integer recoverAfterTimeInS);
	Integer getRecoverAfterTimeInS();

	void setClusterNodes(Set<String> clusterNodes);
	Set<String> getClusterNodes();

	void setElasticPath(String elasticPath);
	@Initializer("'WEB-INF/elastic'")
	String getElasticPath();

}
