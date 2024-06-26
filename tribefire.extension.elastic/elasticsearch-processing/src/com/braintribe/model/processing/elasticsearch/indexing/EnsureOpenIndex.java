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
package com.braintribe.model.processing.elasticsearch.indexing;

import org.elasticsearch.index.IndexNotFoundException;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.elasticsearch.ElasticsearchClient;
import com.braintribe.model.processing.elasticsearch.IndexedElasticsearchConnector;
import com.braintribe.model.processing.elasticsearch.util.ElasticsearchUtils;

public class EnsureOpenIndex implements Runnable {

	private final static Logger logger = Logger.getLogger(EnsureOpenIndex.class);

	private IndexedElasticsearchConnector elasticsearchConnector;

	@Override
	public void run() {

		if (elasticsearchConnector == null) {
			logger.warn(() -> "There is no connector set.");
			return;
		}

		String index = elasticsearchConnector.getIndex();
		ElasticsearchClient client = elasticsearchConnector.getClient();

		logger.debug(() -> "Trying to ensure index " + index);

		try {
			ElasticsearchUtils.openIndex(client, index);

			logger.debug(() -> "Successfully ensured index " + index);

		} catch (IndexNotFoundException e) {
			logger.debug(() -> "Could not find index " + index, e);
		} catch (IllegalStateException e) {
			logger.debug(() -> "Error while trying to open index " + index, e);
		} catch (InterruptedException e) {
			logger.debug(() -> "Got interrupted while trying to open index " + index);
		}
	}

	@Configurable
	@Required
	public void setElasticsearchConnector(IndexedElasticsearchConnector elasticsearchConnector) {
		this.elasticsearchConnector = elasticsearchConnector;
	}

}
