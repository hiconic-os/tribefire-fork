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

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.braintribe.codec.marshaller.api.GmCodec;
import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.common.db.DbVendor;
import com.braintribe.common.db.SimpleDbTestSession;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.jdbc.api.GmDb;
import com.braintribe.gm.jdbc.api.GmRow;
import com.braintribe.model.access.smood.collaboration.distributed.api.sharedstorage.AbstractSharedStorageTest;
import com.braintribe.model.processing.lock.db.impl.DbLockManager;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.RandomTools;
import com.braintribe.utils.stream.api.StreamPipes;

@RunWith(Parameterized.class)
public abstract class AbstractGmDbDcsaSharedStorageTest extends AbstractSharedStorageTest {

	// ###############################################
	// ## . . . . . . . . Static . . . . . . . . . .##
	// ###############################################

	private static SimpleDbTestSession dbTestSession;

	// We user timestamp suffix for table/index names so we can run tests multiple times without re-deploying docker containers
	protected static final String tmSfx = "_" + RandomTools.timeStamp();

	@BeforeClass
	public static void beforeClass() throws Exception {
		deleteResFolderWithDerbyData();

		dbTestSession = SimpleDbTestSession.startDbTest();
	}

	private static void deleteResFolderWithDerbyData() {
		File res = new File("res-gmdb");
		if (res.exists())
			FileTools.deleteDirectoryRecursivelyUnchecked(res);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		dbTestSession.shutdownDbTest();
	}

	// ###############################################
	// ## . . . . . . . . . Tests . . . . . . . . . ##
	// ###############################################

	@Parameters
	public static Object[][] params() {
		return new Object[][] { //
				{ DbVendor.derby }, //
				// { DbVendor.postgres }, //
				// { DbVendor.mysql }, //
				// { DbVendor.mssql }, //
				// { DbVendor.oracle }, //
		};
	}

	private static final GmCodec<Object, String> jsonCodec = new JsonStreamMarshaller();

	protected final DataSource dataSource;
	protected DbLockManager lockManager;
	protected final GmDb gmDb;


	public AbstractGmDbDcsaSharedStorageTest(DbVendor vendor) {
		this.dataSource = dbTestSession.contract.dataSource(vendor);
		this.lockManager = lockManager(dataSource);
		this.gmDb = GmDb.newDb(dataSource) //
				.withDefaultCodec(jsonCodec) //
				.withStreamPipeFactory(StreamPipes.simpleFactory()) //
				.done();
	}

	private DbLockManager lockManager(DataSource dataSource) {
		DbLockManager bean = new DbLockManager();
		bean.setDataSource(dataSource);
		bean.setAutoUpdateSchema(true);
		bean.postConstruct();
		return bean;
	}

	// ###############################################
	// ## . . . . . Query Result Asserts . . . . . .##
	// ###############################################

	protected List<GmRow> queryResult;

	protected void collectResult(Iterable<GmRow> rows) {
		queryResult = newList();

		for (GmRow gmRow : rows)
			queryResult.add(gmRow);
	}

	protected void assertResultSize(int expected) {
		assertThat(queryResult).hasSize(expected);
	}

	protected void assertSameResource(Resource actual, Resource expected) {
		assertThat(actual).isNotNull();

		byte[] actualBytes = toBytes(actual);
		byte[] expectedBytes = toBytes(expected);

		assertThat(actualBytes).containsExactly(expectedBytes);
	}

	protected byte[] toBytes(Resource resource) {
		try (InputStream inputStream = resource.openStream()) {
			return IOTools.slurpBytes(inputStream);
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

}
