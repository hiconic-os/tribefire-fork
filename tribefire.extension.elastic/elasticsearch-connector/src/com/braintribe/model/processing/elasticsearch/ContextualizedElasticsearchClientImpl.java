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
package com.braintribe.model.processing.elasticsearch;

import java.io.Closeable;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;

import com.braintribe.model.processing.session.api.managed.ModelAccessory;

public class ContextualizedElasticsearchClientImpl implements ContextualizedElasticsearchClient, Closeable {

	private final ElasticsearchClient delegate;
	private final ModelAccessory modelAccessory;
	private String index;
	private Integer maxResultWindow;

	public ContextualizedElasticsearchClientImpl(ElasticsearchClient delegate, ModelAccessory modelAccessory) {
		this.delegate = delegate;
		this.modelAccessory = modelAccessory;
	}

	@Override
	public void open() throws Exception {
		delegate.open();
	}

	@Override
	public IndicesAdminClient getIndicesAdminClient() {
		return delegate.getIndicesAdminClient();
	}

	@Override
	public ClusterAdminClient getClusterAdminClient() {
		return delegate.getClusterAdminClient();
	}

	@Override
	public Client elastic() {
		return delegate.elastic();
	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public ModelAccessory getModelAccessory() {
		return modelAccessory;
	}

	@Override
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public Integer getMaxResultWindow() {
		return maxResultWindow;
	}

	public void setMaxResultWindow(Integer maxResultWindow) {
		this.maxResultWindow = maxResultWindow;
	}

}
