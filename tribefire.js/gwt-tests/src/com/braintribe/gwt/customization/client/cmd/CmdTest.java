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
package com.braintribe.gwt.customization.client.cmd;

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import com.braintribe.gwt.customization.client.StartupEntryPoint;
import com.braintribe.gwt.customization.client.cmd.model.Moron;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.prompt.Hidden;
import com.braintribe.model.meta.data.prompt.Visible;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.cmd.CmdResolverImpl;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.util.meta.NewMetaModelGeneration;

/**
 * @author peter.gazdik
 */
public class CmdTest {

	public static void run() {
		new CmdTest(false).runTest();
	}

	public static void runJvm() {
		new CmdTest(true).runTest();
	}

	private final boolean jvm;
	private GmMetaModel metaModel;
	private CmdResolver cmdResolver;

	public CmdTest(boolean jvm) {
		this.jvm = jvm;

	}

	private void runTest() {
		prepareModel();
		prepareResolver();

		runResolver();
	}

	private void prepareModel() {
		log("generating meta model");

		metaModel = new NewMetaModelGeneration().buildMetaModel("test.gwt27.CmdModel", asList(Moron.T));

		log("adding metadata");
		MetaModelEditor editor = new MetaModelEditor(metaModel);

		editor.loadEntityType(Moron.T);
		editor.addEntityMetaData(Hidden.T.create());
	}

	private void prepareResolver() {
		log("preparing CmdResolver");
		cmdResolver = new CmdResolverImpl(new BasicModelOracle(metaModel));
	}

	private void runResolver() {
		log("Resolving entity visibility");
		Visible visibile = cmdResolver.getMetaData().entityClass(Moron.class).meta(Visible.T).exclusive();
		log("Resolved visibile: " + visibile);
	}

	private void log(String msg) {
		if (jvm) {
			System.out.println(msg);
		} else {
			StartupEntryPoint.log(msg);
		}
	}

}
