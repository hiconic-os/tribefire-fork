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
package tribefire.extension.elasticsearch.processing.expert;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.braintribe.logging.Logger;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import tribefire.extension.elasticsearch.model.api.request.doc.DeleteById;
import tribefire.extension.elasticsearch.model.api.request.doc.DeleteRequest;
import tribefire.extension.elasticsearch.model.api.response.SuccessResult;

public class DeleteExpert extends BaseExpert<DeleteRequest, SuccessResult> {

	private static final Logger logger = Logger.getLogger(DeleteExpert.class);

	private String id;

	// ***************************************************************************************************
	// Configuration
	// ***************************************************************************************************

	private void setId(String id) {
		this.id = id;
	}

	@Override
	public SuccessResult process() {
		// Create the low-level client
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		// And create the API client
		ElasticsearchClient client = new ElasticsearchClient(transport);

		try {
			//@formatter:off
			DeleteResponse response = client.delete(i -> i
					.index(this.indexName)
					.id(this.id));
			
			return responseBuilder(SuccessResult.T, this.request)
				.responseEnricher(r -> {
						r.setSuccess(response.result() == Result.Deleted);
				})
				.build();
			//@formatter:on
		} catch (ElasticsearchException | IOException e) {
			logger.error("Error deleting index!", e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	// ***************************************************************************************************
	// Initialization
	// ***************************************************************************************************

	public static DeleteExpert forDeleteById(DeleteById request) {
		return createExpert(DeleteExpert::new, (expert) -> {
			expert.setRequest(request);
			expert.setIndexName(request.getIndexName());
			expert.setId(request.getIndexId());
		});
	}

}