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
package com.braintribe.model.access.smood.collaboration.deployment;

import java.io.File;

import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.model.access.collaboration.distributed.api.DcsaSharedStorage;
import com.braintribe.model.access.smood.collaboration.deployment.wrappers.DistributedCollaborativeSmoodAccess4Test;
import com.braintribe.model.processing.dataio.FileBasedPersistence;
import com.braintribe.provider.Hub;

/**
 * @author peter.gazdik
 */
public class DcsaBuilder extends AbstractCsaBuilder<DistributedCollaborativeSmoodAccess4Test, DcsaDeployedUnit, DcsaBuilder> {

	private File markerFile;

	private DcsaSharedStorage sharedStorage;
	private Hub<String> markerPersistence;

	public static DcsaBuilder create() {
		return new DcsaBuilder();
	}

	// #################################################
	// ## . . . . . . . Fluid methods . . . . . . . . ##
	// #################################################

	@Override
	public DcsaBuilder baseFolder(File baseFolder) {
		super.baseFolder(baseFolder);

		this.markerFile = new File(baseFolder, "marker.txt");
		this.markerPersistence = markerPersistence();

		return self;
	}

	public DcsaBuilder sharedStorage(DcsaSharedStorage sharedStorage) {
		this.sharedStorage = sharedStorage;
		return self;
	}

	// #################################################
	// ## . . . . . Implementation providers . . . . .##
	// #################################################

	@Override
	protected DistributedCollaborativeSmoodAccess4Test newCsa() {
		DistributedCollaborativeSmoodAccess4Test result = new DistributedCollaborativeSmoodAccess4Test();
		result.setSharedStorage(sharedStorage);
		result.setCsaStatePersistence(statePersistence);
		result.setBinaryPersistenceEventSource(binaryProcessor);

		return result;
	}

	@Override
	protected DcsaDeployedUnit newUnit() {
		DcsaDeployedUnit result = new DcsaDeployedUnit();
		result.sharedStorage = sharedStorage;
		result.markerPersistence = markerPersistence;

		return result;
	}

	// #################################################
	// ## . . . . . . . . . Helpers . . . . . . . . . ##
	// #################################################

	protected Hub<String> markerPersistence() {
		return fileBasedPersistence(markerFile);
	}

	private <T> Hub<T> fileBasedPersistence(File file) {
		FileBasedPersistence<T> bean = new FileBasedPersistence<>();
		bean.setSerializationOptions(GmSerializationOptions.defaultOptions.derive().setOutputPrettiness(OutputPrettiness.high).build());
		bean.setFile(file);
		bean.setMarshaller(new JsonStreamMarshaller());
		
		return bean;
	}
	
	@Override
	protected void validate() {
		super.validate();

		checkConfigured(sharedStorage, "sharedStorage");
		checkConfigured(markerPersistence, "markerPersistence");
		checkConfigured(markerPersistence, "markerPersistence");
	}

}
