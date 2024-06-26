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
package com.braintribe.model.elasticsearchreflection;

import com.braintribe.model.elasticsearchreflection.clusterhealth.ClusterHealth;
import com.braintribe.model.elasticsearchreflection.nodeinformation.NodeInformation;
import com.braintribe.model.elasticsearchreflection.nodestats.NodeStatsInfo;
import com.braintribe.model.elasticsearchreflection.tasks.PendingClusterTasks;
import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 *
 * Maps Elasticsearch reflection information to TF model structure. <br/>
 * Since it was started in Elasticsearch 2.2.1 it does not reflect the complecte ClusterAdminClient structure. This is
 * the mapping:
 *
 * <table>
 * <tr>
 * <th>ES function</th>
 * <th>TF type</th>
 * <th>Version style</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td>clusterAdminClient.prepareNodesHotThreads()</td>
 * <td>{@link ClusterHealth}</td>
 * <td>2.2.1, 5.4</td>
 * </tr>
 * <tr>
 * <td>client.execute(NodesInfoAction.INSTANCE, new NodesInfoRequest())</td>
 * <td>{@link NodeInformation}</td>
 * <td>2.2.1</td>
 * <td>Old style - needs adaptation</td>
 * </tr>
 * <tr>
 * <td>clusterAdminClient.preparePendingClusterTasks()</td>
 * <td>{@link PendingClusterTasks}</td>
 * <td>5.4</td>
 * </tr>
 * <tr>
 * <td>clusterAdminClient.prepareNodesStats()</td>
 * <td>{@link NodeStatsInfo}</td>
 * <td>5.4</td>
 * <td>Added only partial information - looks like many duplicates</td>
 * </tr>
 * </table>
 *
 */
public interface ElasticsearchReflection extends StandardIdentifiable {

	final EntityType<ElasticsearchReflection> T = EntityTypes.T(ElasticsearchReflection.class);

	ClusterHealth getClusterHealth();
	void setClusterHealth(ClusterHealth clusterHealth);

	NodeInformation getNodeInformation();
	void setNodeInformation(NodeInformation nodeInformation);

	PendingClusterTasks getPendingClusterTasks();
	void setPendingClusterTasks(PendingClusterTasks pendingClusterTasks);

	NodeStatsInfo getNodeStatsInfo();
	void setNodeStatsInfo(NodeStatsInfo nodeStatsInfo);

	void setElasticsearchReflectionError(ElasticsearchReflectionError elasticsearchReflectionError);
	ElasticsearchReflectionError getElasticsearchReflectionError();

}
