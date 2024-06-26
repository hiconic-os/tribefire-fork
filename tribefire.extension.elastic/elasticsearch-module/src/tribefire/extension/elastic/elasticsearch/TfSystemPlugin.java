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
package tribefire.extension.elastic.elasticsearch;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.common.io.stream.NamedWriteableRegistry;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.BigArrays;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.http.HttpServerTransport;
import org.elasticsearch.indices.breaker.CircuitBreakerService;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.NetworkPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.watcher.ResourceWatcherService;

import tribefire.extension.elastic.elasticsearch.wares.NodeServlet;

/**
 * This is a plugin that is need to access the inner objects of the Elastic {@link Node}. The constructor of this
 * {@link Node} creates {@link NamedXContentRegistry} and a {@link RestController} that we need access to in the
 * {@link NodeServlet}. Hence, this plugin implements those methods that are executed by the Node constructor and keeps
 * the information that we need for later access (see {@link NodeServlet#postConstruct()}). This way of accessing things
 * replaces the older way of using reflection.
 */
public class TfSystemPlugin extends Plugin implements NetworkPlugin {

	private static NamedXContentRegistry xContentRegistry = null;
	private static RestController restController = null;

	@Override
	public Collection<Object> createComponents(Client client, ClusterService clusterService, ThreadPool threadPool,
			ResourceWatcherService resourceWatcherService, ScriptService scriptService, NamedXContentRegistry xContentRegistry) {
		TfSystemPlugin.xContentRegistry = xContentRegistry;
		return Collections.emptyList();
	}

	public static NamedXContentRegistry getxContentRegistry() {
		return xContentRegistry;
	}

	@Override
	public Map<String, Supplier<HttpServerTransport>> getHttpTransports(Settings settings, ThreadPool threadPool, BigArrays bigArrays,
			CircuitBreakerService circuitBreakerService, NamedWriteableRegistry namedWriteableRegistry, NamedXContentRegistry xContentRegistry,
			NetworkService networkService, HttpServerTransport.Dispatcher dispatcher) {
		if (dispatcher instanceof RestController) {
			TfSystemPlugin.restController = (RestController) dispatcher;
		}
		return Collections.emptyMap();
	}

	public static RestController getRestController() {
		return restController;
	}

}
