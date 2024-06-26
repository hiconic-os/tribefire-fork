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
package com.braintribe.codec.marshaller.yaml.tfruntime;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Test;

import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.TypeExplicitness;
import com.braintribe.codec.marshaller.api.TypeExplicitnessOption;
import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.Backend;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.Component;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.EnvironmentVariable;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.LogLevel;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.Metadata;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.Resources;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.Spec;
import com.braintribe.codec.marshaller.yaml.tfruntime.model.TribefireRuntime;
import com.braintribe.testing.test.AbstractTest;

public class TfRuntimeYamlMarshallerTest extends AbstractTest {

	@Test
	public void test() throws Exception {

		TribefireRuntime tribefireRuntime = createTribefireRuntime();

		YamlMarshaller marshaller = new YamlMarshaller();
		marshaller.setV2(true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		//@formatter:off
		GmSerializationOptions options = GmSerializationOptions.deriveDefaults()
				.set(TypeExplicitnessOption.class, TypeExplicitness.polymorphic)
				.inferredRootType(TribefireRuntime.T)
				.build();
		//@formatter:on

		marshaller.marshall(baos, tribefireRuntime, options);

		String mashalledTribefireRuntime = baos.toString();

		System.out.println(mashalledTribefireRuntime);

		File expectedOutcome = testFile("expected.yml");
		assertThat(expectedOutcome).hasContent(mashalledTribefireRuntime);

	}

	@Test
	public void testTypeSafe() throws Exception {

		TribefireRuntime tribefireRuntime = createTribefireRuntime();

		YamlMarshaller marshaller = new YamlMarshaller();
		marshaller.setV2(true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		GmSerializationOptions options = GmSerializationOptions.deriveDefaults().inferredRootType(TribefireRuntime.T).build();

		marshaller.marshall(baos, tribefireRuntime, options);

		String mashalledTribefireRuntime = baos.toString();

		System.out.println(mashalledTribefireRuntime);

		File expectedOutcome = testFile("expected-type-safe.yml");
		assertThat(expectedOutcome).hasContent(mashalledTribefireRuntime);

	}

	private TribefireRuntime createTribefireRuntime() {
		TribefireRuntime tribefireRuntime = TribefireRuntime.T.create();
		tribefireRuntime.setApiVersion("tribefire.cloud/v1alpha1");
		tribefireRuntime.setKind("TribefireRuntime");

		Metadata metadata = Metadata.T.create();
		metadata.setName("demo-fire");
		metadata.setNamespace("tribefire");
		metadata.getLabels().put("stage", "dev");
		tribefireRuntime.setMetadata(metadata);

		Spec spec = Spec.T.create();
		tribefireRuntime.setSpec(spec);

		spec.setDomain("tribefire.local");
		spec.setDatabaseType("local");

		Backend backend = Backend.T.create();
		spec.setBackend(backend);
		backend.setType("etcd");
		backend.getParams().put("url", "http://tf-etcd-cluster-client.etcd:2379");

		Component masterComponent = Component.T.create();
		spec.getComponents().add(masterComponent);
		masterComponent.setName("tribefire-master");
		masterComponent.setType("Services");
		masterComponent.setLogLevel(LogLevel.DEBUG);
		masterComponent.setLogJson(true);
		masterComponent.setPublicUrl("http://demo.tribefire.local");
		EnvironmentVariable environmentVariable = EnvironmentVariable.T.create();
		environmentVariable.setName("TRIBEFIRE_CUSTOM");
		environmentVariable.setValue("true");
		masterComponent.getEnv().add(environmentVariable);
		Resources masterComponentResources = Resources.T.create();
		masterComponent.setResources(masterComponentResources);
		masterComponentResources.getRequests().put("memory", "512Mi");
		masterComponentResources.getRequests().put("cpu", "500m");
		masterComponentResources.getLimits().put("memory", "2048Mi");
		masterComponentResources.getLimits().put("cpu", "2000m");

		Component controlCenterComponent = Component.T.create();
		spec.getComponents().add(controlCenterComponent);
		controlCenterComponent.setName("tribefire-control-center");
		controlCenterComponent.setType("ControlCenter");
		controlCenterComponent.setLogLevel(LogLevel.INFO);
		controlCenterComponent.setLogJson(true);
		controlCenterComponent.setPublicUrl("http://demo.tribefire.local");

		return tribefireRuntime;
	}

}
