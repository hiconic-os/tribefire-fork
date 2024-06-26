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
package com.braintribe.model.access;

import org.junit.Test;

import com.braintribe.model.access.model.Book;
import com.braintribe.model.accessapi.ManipulationRequest;
import com.braintribe.model.accessapi.ManipulationResponse;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.PropertyQueryResult;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;
import com.braintribe.testing.tools.gm.GmTestTools;
import com.braintribe.utils.genericmodel.GMCoreTools;

/**
 * Tests a manipulation which contains an entity reference to an entity the access doesn't know yet (and thus has to
 * load it from the delegate).
 * 
 * @author michael.lafite
 */
public class BasicAccessAdapterUnknownEntityReferencedInManipulationTest {

	@Test
	public void test() throws Exception {

		IncrementalAccess delegateAccess = GmTestTools.newSmoodAccessMemoryOnly();
		PersistenceGmSession delegateSession = new BasicPersistenceGmSession(delegateAccess);
		TestAccess access = new TestAccess(delegateAccess);

		Book book = delegateSession.create(Book.T);
		book.setId(0l);
		delegateSession.commit();

		PersistentEntityReference bookReference = book.reference();

		EntityProperty bookPagesProperty = EntityProperty.T.create();
		bookPagesProperty.setPropertyName("pages");
		bookPagesProperty.setReference(bookReference);

		// LocalEntityProperty bookPagesProperty = LocalEntityProperty.T.create();
		// bookPagesProperty.setPropertyName("pages");
		// bookPagesProperty.setEntity(book);

		ChangeValueManipulation manipulation = ChangeValueManipulation.T.create();
		manipulation.setNewValue(2);
		manipulation.setOwner(bookPagesProperty);

		ManipulationRequest manipulationRequest = ManipulationRequest.T.create();
		manipulationRequest.setManipulation(manipulation);

		access.applyManipulation(manipulationRequest);
	}

	private static class TestAccess extends BasicAccessAdapter {

		private final IncrementalAccess delegate;

		public TestAccess(IncrementalAccess delegate) {
			this.delegate = delegate;
		}

		@Override
		public ManipulationResponse applyManipulation(ManipulationRequest manipulationRequest)
				throws ModelAccessException {
			println("Received manipulation request.");
			return super.applyManipulation(manipulationRequest);
		}

		@Override
		protected void save(AdapterManipulationReport manipulationReport) throws ModelAccessException {
			println("Received manipulation report. (Ignored by this test.)");
		}

		@Override
		public EntityQueryResult queryEntities(EntityQuery query) throws ModelAccessException {
			println("Received entity query:\n" + GMCoreTools.getDescription(query));
			return super.queryEntities(query);
		}

		@Override
		public PropertyQueryResult queryProperty(PropertyQuery query) throws ModelAccessException {
			throw new RuntimeException("Unexpectedly received property query:\n" + GMCoreTools.getDescription(query));
		}

		@Override
		public SelectQueryResult query(SelectQuery query) throws ModelAccessException {
			println("Received select query:\n" + GMCoreTools.getDescription(query));
			SelectQueryResult result = this.delegate.query(query);
			return result;
		}

		@SuppressWarnings("unused")
		private void println(String msg) {
			// System.out.println(msg);
		}
	}

}
