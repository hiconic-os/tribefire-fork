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
package tribefire.extension.elasticsearch.test.service;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.processing.session.api.resource.ResourceCreateBuilder;
import com.braintribe.model.resource.Resource;
import com.braintribe.testing.category.VerySlow;
import com.braintribe.utils.FileTools;

import tribefire.extension.elasticsearch.model.api.request.doc.DeleteById;
import tribefire.extension.elasticsearch.model.api.response.IndexResponse;
import tribefire.extension.elasticsearch.model.api.response.SuccessResult;
import tribefire.extension.elasticsearch.test.AbstractElasticsearchTest;

@Category(VerySlow.class)
public class IndexTest extends AbstractElasticsearchTest {

	private String primaryIndexName;
	private String secondaryIndexName;

	@Before
	public void setup() {
		primaryIndexName = UUID.randomUUID().toString();
		createIndex(request -> {
			request.setIndexName(primaryIndexName);
		});
	}

	@After
	public void cleanup() {
		deleteIndex(request -> {
			request.setIndexName(primaryIndexName);
		});
	}

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testIndexResourceSingleIndex() {
		indexResources(request -> {
			request.setIndexNames(asList(primaryIndexName));

			ResourceCreateBuilder resourceBuilder = cortexSession.resources().create().name(pdf.getName());
			Resource resource = FileTools.read(pdf).fromInputStream(resourceBuilder::store);
			request.setResources(asList(resource));
		});
	}

	@Test
	public void testIndexResourcesSingleIndex() {
		indexResources(request -> {
			request.setIndexNames(asList(primaryIndexName));

			ResourceCreateBuilder resourceBuilder = cortexSession.resources().create().name(pdf.getName());
			Resource r1 = FileTools.read(pdf).fromInputStream(resourceBuilder::store);

			resourceBuilder = cortexSession.resources().create().name(pdf_simple.getName());
			Resource r2 = FileTools.read(pdf_simple).fromInputStream(resourceBuilder::store);
			request.setResources(asList(r1, r2));
		});
	}

	@Test
	public void testIndexResourceMultipleIndexes() {
		secondaryIndexName = UUID.randomUUID().toString();

		createIndex(request -> {
			request.setIndexName(secondaryIndexName);
		});

		indexResources(request -> {
			request.setIndexNames(asList(primaryIndexName, secondaryIndexName));

			ResourceCreateBuilder resourceBuilder = cortexSession.resources().create().name(pdf.getName());
			Resource resource = FileTools.read(pdf).fromInputStream(resourceBuilder::store);
			request.setResources(asList(resource));
		});

		deleteIndex(request -> {
			request.setIndexName(secondaryIndexName);
		});
	}

	@Test
	public void testIndexResourcesMultipleIndexes() {
		secondaryIndexName = UUID.randomUUID().toString();

		createIndex(request -> {
			request.setIndexName(secondaryIndexName);
		});

		indexResources(request -> {
			request.setIndexNames(asList(primaryIndexName, secondaryIndexName));

			ResourceCreateBuilder resourceBuilder = cortexSession.resources().create().name(pdf.getName());
			Resource r1 = FileTools.read(pdf).fromInputStream(resourceBuilder::store);

			resourceBuilder = cortexSession.resources().create().name(pdf_simple.getName());
			Resource r2 = FileTools.read(pdf_simple).fromInputStream(resourceBuilder::store);
			request.setResources(asList(r1, r2));
		});

		deleteIndex(request -> {
			request.setIndexName(secondaryIndexName);
		});
	}

	@Test
	public void testDeleteIndexById() {
		IndexResponse response = indexResources(request -> {
			request.setIndexNames(asList(primaryIndexName));

			ResourceCreateBuilder resourceBuilder = cortexSession.resources().create().name(pdf.getName());
			Resource resource = FileTools.read(pdf).fromInputStream(resourceBuilder::store);
			request.setResources(asList(resource));
		});

		DeleteById deleteRequest = DeleteById.T.create();
		deleteRequest.setIndexName(primaryIndexName);
		deleteRequest.setIndexId(response.getItems().get(0).getIndexId());

		SuccessResult result = (SuccessResult) deleteRequest.eval(cortexSession).get();
		assertEquals(true, result.getSuccess());
	}

	// ***************************************************************************************************
	// HELPERS
	// ***************************************************************************************************

	// -----------------------------------------------------------------------
	// ASSERTIONS
	// -----------------------------------------------------------------------

}
