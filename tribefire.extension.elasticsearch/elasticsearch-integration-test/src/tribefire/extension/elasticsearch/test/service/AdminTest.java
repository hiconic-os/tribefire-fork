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

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.VerySlow;

import tribefire.extension.elasticsearch.model.api.request.admin.CreateIndexByName;
import tribefire.extension.elasticsearch.model.api.request.admin.DeleteIndexByName;
import tribefire.extension.elasticsearch.model.api.response.SuccessResult;
import tribefire.extension.elasticsearch.test.AbstractElasticsearchTest;

@Category(VerySlow.class)
public class AdminTest extends AbstractElasticsearchTest {

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testCreateIndexByName() {
		String uniqueName = UUID.randomUUID().toString();

		CreateIndexByName createRequest = CreateIndexByName.T.create();
		createRequest.setIndexName(uniqueName);

		SuccessResult result = (SuccessResult) createRequest.eval(cortexSession).get();
		assertEquals(true, result.getSuccess());

		DeleteIndexByName deleteRequest = DeleteIndexByName.T.create();
		deleteRequest.setIndexName(uniqueName);

		result = (SuccessResult) deleteRequest.eval(cortexSession).get();
		assertEquals(true, result.getSuccess());
	}

}
