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
package com.braintribe.model.processing.test.assembly.sync;

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Before;

import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.assembly.sync.impl.AssemblyImporter;
import com.braintribe.model.processing.assembly.sync.impl.GenericImporterContext;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.model.processing.test.assembly.sync.api.AssemblyInitializer;
import com.braintribe.model.processing.test.assembly.sync.model.AssemblyImporterTestModel;
import com.braintribe.model.processing.test.assembly.sync.model.AssemblyNode;
import com.braintribe.model.processing.traversing.engine.api.customize.PropertyTransferExpert;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.testing.tools.gm.GmTestTools;

/**
 * @author peter.gazdik
 */
public abstract class AbstractAssemblySyncTest {

	protected static final Predicate<GenericEntity> ALWAYS_FALSE = (s -> false);
	protected static final Predicate<GenericEntity> NO_ENVELOPE = ALWAYS_FALSE;
	protected static final Predicate<GenericEntity> NO_EXTERNAL = ALWAYS_FALSE;

	protected static final boolean NO_INCLUDE_ENVELOPE = false;
	protected static final String ROOT_NAME = "root";

	protected AssemblyNode assembly = newFreeNode(ROOT_NAME);
	protected AssemblyNode importedAssembly;

	protected SmoodAccess smoodAccess;
	protected PersistenceGmSession session;
	protected PersistenceGmSession dbSession; // this is here to create data which already exists in the target DB

	@Before
	public void setup() {
		smoodAccess = GmTestTools.newSmoodAccessMemoryOnly("testAccess", AssemblyImporterTestModel.model());
		dbSession = newSession();
		session = newSession();
	}

	private PersistenceGmSession newSession() {
		return new BasicPersistenceGmSession(smoodAccess);
	}

	protected void commitToDb() {
		try {
			dbSession.commit();

		} catch (GmSessionException e) {
			throw new RuntimeException("Error while initializing data.", e);
		}
	}

	// ############################################
	// ## . . . . . Initialization phase . . . . ##
	// ############################################

	protected void prepareDb(AssemblyInitializer initializer) {
		try {
			initializer.run(this::newNodeInDb);
			commitToDb();

		} catch (Exception e) {
			throw new RuntimeException("Error while preparing data in DB.", e);
		}
	}

	protected void prepareExport(AssemblyInitializer initializer) {
		try {
			initializer.run(this::newFreeNode);
		} catch (Exception e) {
			throw new RuntimeException("Error while preparing free data.", e);
		}
	}

	protected void prepareSession(AssemblyInitializer initializer) {
		try {
			initializer.run(this::newSessionNode);
		} catch (Exception e) {
			throw new RuntimeException("Error while preparing free data.", e);
		}
	}

	// ############################################
	// ## . . . . . . Creating nodes . . . . . . ##
	// ############################################

	private AssemblyNode newFreeNode(String name) {
		return newExportNode(name, name);
	}

	/**
	 * In case we want to create a free node with different name and globalId, we use this method. Since it only makes sense inside
	 * {@link #prepareExport(AssemblyInitializer)}, we call it newExportNode.
	 */
	protected AssemblyNode newExportNode(String globalId, String name) {
		return init(AssemblyNode.T::create, globalId, name);
	}

	private AssemblyNode newSessionNode(String name) {
		return init(() -> session.create(AssemblyNode.T), name, name);
	}

	private AssemblyNode newNodeInDb(String name) {
		return init(() -> dbSession.create(AssemblyNode.T), name, name);
	}

	private AssemblyNode init(Supplier<AssemblyNode> factory, String globalId, String name) {
		AssemblyNode result = factory.get();
		result.setGlobalId(globalId);
		result.setName(name);
		return result;
	}

	protected void setNeighbors(AssemblyNode node, AssemblyNode... neighbors) {
		node.setNeighbors(asList(neighbors));
	}

	// ############################################
	// ## . . . . . Running the import . . . . . ##
	// ############################################

	protected void runImport(boolean includeEnvelope, Predicate<GenericEntity> isEnvelope, Predicate<GenericEntity> isExternal) {
		runImport(includeEnvelope, isEnvelope, isExternal, null);
	}

	protected void runImport(boolean includeEnvelope, Predicate<GenericEntity> isEnvelope, Predicate<GenericEntity> isExternal,
			PropertyTransferExpert pte) {

		importAssembly(includeEnvelope, isEnvelope, isExternal, pte);
		queryImportedAssembly();
	}

	private void importAssembly(boolean includeEnvelope, Predicate<GenericEntity> isEnvelope, Predicate<GenericEntity> isExternal,
			PropertyTransferExpert pte) {

		GenericImporterContext<AssemblyNode> context = new GenericImporterContext<>();
		context.setSession(session);
		context.setAssembly(assembly);
		context.setIncludeEnvelope(includeEnvelope);
		context.setEnvelopePredicate(isEnvelope);
		context.setExternalPredicate(isExternal);
		context.setPropertyTransferExpert(pte);

		AssemblyImporter.importAssembly(context);
	}

	private void queryImportedAssembly() {
		EntityQuery eq = EntityQueryBuilder.from(AssemblyNode.T).where().property("name").eq(ROOT_NAME).done();

		try {
			importedAssembly = session.queryCache().entities(eq).unique();

		} catch (GmSessionException e) {
			throw new RuntimeException("Error with querying session cache for imported assembly.", e);
		}
	}
}
