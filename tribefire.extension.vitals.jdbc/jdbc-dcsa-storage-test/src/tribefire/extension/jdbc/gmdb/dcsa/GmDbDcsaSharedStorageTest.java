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
package tribefire.extension.jdbc.gmdb.dcsa;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.braintribe.common.db.DbVendor;
import com.braintribe.model.access.collaboration.distributed.api.DcsaSharedStorage;
import com.braintribe.model.resource.Resource;

/**
 * Tests for {@link GmDbDcsaSharedStorage}
 */
public class GmDbDcsaSharedStorageTest extends AbstractGmDbDcsaSharedStorageTest {

	public GmDbDcsaSharedStorageTest(DbVendor vendor) {
		super(vendor);
	}

	@Override
	protected boolean supportsReadResources() {
		return true;
	}

	// @formatter:off
	@Override @Test	public void readNothingFromEmptyStorage() throws Exception {
		super.readNothingFromEmptyStorage();
	}

	@Override @Test	public void storeSingleOperation() throws Exception {
		super.storeSingleOperation();
	}

	@Override @Test	public void storeMultipleOperations() throws Exception {
		super.storeMultipleOperations();
	}

	@Override @Test	public void storeAndReadResource() throws Exception {
		super.storeAndReadResource();
	}
	// @formatter:on

	@Test
	public void storeAndReadResourceOnSamePath() throws Exception {
		final String content1 = "DUMMY RESOURCE 1";
		final String content2 = "DUMMY RESOURCE 2";
		final String content3 = "DUMMY RESOURCE 3";

		final String path = "res/path";

		// Create binary resources
		storeResource(path, content1);
		assertResource(path, content1);

		// These two also log a warning that the path is already used.
		storeResource(path, content2);
		assertResource(path, content2);

		storeResource(path, content3);
		assertResource(path, content3);
	}

	private void assertResource(String path, String content) throws Exception {
		Map<String, Resource> resources;
		resources = readResouces(path);
		assertResourceContent(resources.get(path), content);
	}

	private String gmml;

	@Override
	@Test
	public void storeResourceBasedOperations() throws Exception {
		this.gmml = "SHORT GMML";
		super.storeResourceBasedOperations();
	}

	@Test
	public void storeResourceBasedOperations_LongGmml() throws Exception {
		this.gmml = str128K();
		super.storeResourceBasedOperations();
	}

	@Override
	protected String storeResourceBasedOperationsFileContent() {
		return gmml;
	}

	@Override
	protected DcsaSharedStorage newDcsaSharedStorage() {
		GmDbDcsaSharedStorage storage = new GmDbDcsaSharedStorage();

		storage.setProjectId("storage-test-" + UUID.randomUUID().toString());
		storage.setGmDb(gmDb);
		storage.setLockManager(lockManager);

		storage.postConstruct();

		return storage;
	}

	// Helpers

	protected String str128K() {
		return doubleNTimes(str1K(), 7);
	}

	protected String str1K() {
		String s = str8Chars();
		s = doubleNTimes(s, 7);
		return s.substring(0, 1000);
	}

	protected String str8Chars() {
		return "HÆllo!!!";
	}

	protected String doubleNTimes(String s, int n) {
		while (n-- > 0)
			s += s;
		return s;
	}

}
