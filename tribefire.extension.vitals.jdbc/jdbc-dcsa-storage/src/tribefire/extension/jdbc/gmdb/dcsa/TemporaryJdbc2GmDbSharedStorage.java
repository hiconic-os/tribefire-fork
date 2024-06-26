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

import static com.braintribe.util.jdbc.JdbcTools.tableExists;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.braintribe.logging.Logger;
import com.braintribe.model.access.collaboration.distributed.api.DcsaIterable;
import com.braintribe.model.access.collaboration.distributed.api.DcsaSharedStorage;
import com.braintribe.model.access.collaboration.distributed.api.JdbcDcsaStorage;
import com.braintribe.model.access.collaboration.distributed.api.model.CsaOperation;
import com.braintribe.model.resource.Resource;

/**
 * A temporary {@link DcsaSharedStorage} implementation which is backed by the old {@link JdbcDcsaStorage} and new
 * {@link GmDbDcsaSharedStorage} implementations. It delegates to GMDB for the most time, just at the very first attempt
 * to access that data it sees the data is not there, it retrieves it from JDBC impl and stores in the GMDB one.
 * 
 * @author peter.gazdik
 */
public class TemporaryJdbc2GmDbSharedStorage implements DcsaSharedStorage {

	public static final String OLD_TABLE_NAME = "TF_DCSA";

	private static final Logger log = Logger.getLogger(TemporaryJdbc2GmDbSharedStorage.class);

	public DataSource dataSource;
	public JdbcDcsaStorage jdbcStorage;
	public GmDbDcsaSharedStorage gmDbStorage;
	private ReentrantLock upgradeLock = new ReentrantLock();

	private Boolean isUpgraded;

	// @formatter:off
	public void setDataSource(DataSource dataSource) { this.dataSource = dataSource; }
	public void setJdbcStorage(JdbcDcsaStorage jdbcStorage) { this.jdbcStorage = jdbcStorage; }
	public void setGmDbStorage(GmDbDcsaSharedStorage gmDbStorage) { this.gmDbStorage = gmDbStorage; }
	// @formatter:on

	@Override
	public Lock getLock(String accessId) {
		return gmDbStorage.getLock(accessId);
	}

	@Override
	public String storeOperation(String accessId, CsaOperation csaOperation) {
		return storage().storeOperation(accessId, csaOperation);
	}

	@Override
	public DcsaIterable readOperations(String accessId, String lastReadMarker) {
		return storage().readOperations(accessId, lastReadMarker);
	}

	@Override
	public Map<String, Resource> readResource(String accessId, Collection<String> storedResourcesPaths) {
		return storage().readResource(accessId, storedResourcesPaths);
	}

	private DcsaSharedStorage storage() {
		return isUpgraded() ? gmDbStorage : jdbcStorage;
	}

	public boolean isGmDbImplementation() {
		return isUpgraded();
	}

	private boolean isUpgraded() {
		if (isUpgraded == null) {
			upgradeLock.lock();
			try {
				if (isUpgraded == null) {
					isUpgraded = checkUpgradeAndMaybeEnsureNewTable();
				}
			} finally {
				upgradeLock.unlock();
			}
		}

		return isUpgraded.booleanValue();
	}

	private Boolean checkUpgradeAndMaybeEnsureNewTable() {
		boolean result = computeIsUpgraded();
		if (result)
			gmDbStorage.ensureTable();

		return result;
	}

	private boolean computeIsUpgraded() {
		try {
			Connection c = dataSource.getConnection();

			// if new table exists or old table does not exists, we use the new table
			boolean newExists = tableExists(c, GmDbDcsaSharedStorage.DEFAULT_OPS_TABLE_NAME) != null;
			if (newExists) {
				log("Using GmDb implementation as it's table '" + GmDbDcsaSharedStorage.DEFAULT_OPS_TABLE_NAME + "' already exists.");
				return true;
			}

			boolean oldExists = tableExists(c, OLD_TABLE_NAME) != null;
			if (oldExists) {
				log("Using old JDBC implementation as it's table '" + OLD_TABLE_NAME + "' already exists and the new table '"
						+ GmDbDcsaSharedStorage.DEFAULT_OPS_TABLE_NAME + "' does not exist");
				return false;
			}

			log("Using GmDb implementation as neither old nor new table exists yet.");
			return true;

		} catch (SQLException e) {
			throw new RuntimeException("Error while checking which tables exist for JDBC DCSA storage.", e);
		}
	}

	private void log(String msg) {
		log.info("[DCSA] " + msg);
	}

}
