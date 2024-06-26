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
package com.braintribe.swagger.test.utils;

import org.junit.After;
import org.junit.Before;

import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.product.rat.imp.ImpApi;
import com.braintribe.testing.internal.tribefire.tests.AbstractTribefireQaTest;

/**
 * Creates a new Smood access and provides
 * subclasses with <br>
 * a session to it: {@link #demoAccessSession}<br>
 * a session factory: {@link #factory}<br>
 * a {@link CortexMetaModelManipulationHelper}: {@link #cortex}<br>
 * <br>
 * <br>
 * undeploys the created accesss in the end
 * 
 *
 */
public abstract class AbstractSwaggerImportTest extends AbstractTribefireQaTest {
	public static final String CORTEX_ACCESS_ID = "cortex";

	protected PersistenceGmSession swaggerImportAccessSession;
	protected GmMetaModel configuredDemoModel;
	protected ImpApi globalImp;
	protected PersistenceGmSessionFactory globalSessionFactory;
	
	@Before
	public void initBase() throws GmSessionException {
		logger.info("Preparing Test for Swagger Model Import module");

		globalImp = apiFactory().build();
		globalSessionFactory = apiFactory().buildSessionFactory();
		
		swaggerImportAccessSession = globalImp.session();
		
		logger.info("##################################### actual test begins #############################");
	}

	@After
	public void tearDown() {
		logger.info("##################################### tear down #############################");
		eraseTestEntities();
	}
}
