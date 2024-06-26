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
package com.braintribe.model.processing.gcp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.processing.gcp.connect.GcpStorageConnectorImpl;
import com.braintribe.model.processing.resource.server.test.commons.TestResourceAccessFactory;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resourceapi.persistence.StoreBinaryResponse;
import com.braintribe.model.resourceapi.stream.GetBinaryResponse;
import com.braintribe.model.resourceapi.stream.StreamBinaryResponse;
import com.braintribe.model.resourceapi.stream.range.StreamRange;
import com.braintribe.testing.category.SpecialEnvironment;
import com.braintribe.testing.tools.gm.GmTestTools;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.RandomTools;

@Category(SpecialEnvironment.class)
public class MassStorageGcpTest {

	private static GcpStorageConnectorImpl connection;
	private final static String bucketName = "playground-rku";

	@BeforeClass
	public static void beforeClass() throws Exception {
		GcpConnector deployable = GcpConnector.T.create();
		InputStream inputStream = MassStorageGcpTest.class.getClassLoader()
				.getResourceAsStream("com/braintribe/model/processing/gcp/service/gcp-test-service-account.json");
		String jsonCredentials = IOTools.slurp(inputStream, "UTF-8");
		deployable.setJsonCredentials(jsonCredentials);

		connection = new GcpStorageConnectorImpl();
		connection.setConnector(deployable);
	}

	@Test
	public void testBinaryProcessorRepeatedStore() throws Exception {

		GcpStorageBinaryProcessor processor = new GcpStorageBinaryProcessor();
		processor.setConnector(connection);
		processor.setBucketName(bucketName);
		processor.setPathPrefix("l-and-p");

		PersistenceGmSession session = GmTestTools.newSessionWithSmoodAccessMemoryOnly();
		TestResourceAccessFactory accessFactory = new TestResourceAccessFactory();
		((BasicPersistenceGmSession) session).setResourcesAccessFactory(accessFactory);

		int iterations = 100;
		Random rnd = new Random();

		for (int i = 0; i < iterations; ++i) {

			byte[] source = new byte[1024];
			rnd.nextBytes(source);
			String id = RandomTools.newStandardUuid();

			Resource storeResource = session.resources().create().name("test-" + id + ".bin").store(new ByteArrayInputStream(source));

			StoreBinaryResponse response = processor.store(storeResource, session);

			Resource storedResource = response.getResource();

			assertThat(storedResource).isNotNull();
		}
	}

	@Test
	public void testBinaryProcessorRepeatedGet() throws Exception {

		GcpStorageBinaryProcessor processor = new GcpStorageBinaryProcessor();
		processor.setConnector(connection);
		processor.setBucketName(bucketName);
		processor.setPathPrefix("l-and-p");

		PersistenceGmSession session = GmTestTools.newSessionWithSmoodAccessMemoryOnly();
		TestResourceAccessFactory accessFactory = new TestResourceAccessFactory();
		((BasicPersistenceGmSession) session).setResourcesAccessFactory(accessFactory);

		byte[] source = new byte[1024];
		new Random().nextBytes(source);

		Resource storeResource = session.resources().create().name("test.bin").store(new ByteArrayInputStream(source));
		storeResource.setFileSize((long) source.length);

		StoreBinaryResponse response = processor.store(storeResource, session);

		Resource storedResource = response.getResource();

		assertThat(storedResource).isNotNull();

		int iterations = 100;
		for (int i = 0; i < iterations; ++i) {
			byte[] actual = downloadResourceGet(session, processor, storedResource, null, null);

			assertThat(actual).isEqualTo(source);
		}
	}

	@Test
	public void testBinaryProcessorRepeatedStream() throws Exception {

		GcpStorageBinaryProcessor processor = new GcpStorageBinaryProcessor();
		processor.setConnector(connection);
		processor.setBucketName(bucketName);

		PersistenceGmSession session = GmTestTools.newSessionWithSmoodAccessMemoryOnly();
		TestResourceAccessFactory accessFactory = new TestResourceAccessFactory();
		((BasicPersistenceGmSession) session).setResourcesAccessFactory(accessFactory);

		byte[] source = new byte[1024];
		new Random().nextBytes(source);

		Resource storeResource = session.resources().create().name("test.bin").store(new ByteArrayInputStream(source));
		storeResource.setFileSize((long) source.length);

		StoreBinaryResponse response = processor.store(storeResource, session);

		Resource storedResource = response.getResource();

		assertThat(storedResource).isNotNull();

		int iterations = 100;
		for (int i = 0; i < iterations; ++i) {

			byte[] actual = downloadResourceStream(session, processor, storedResource, null, null);

			assertThat(actual).isEqualTo(source);
		}

	}

	protected byte[] downloadResourceGet(PersistenceGmSession session, GcpStorageBinaryProcessor processor, Resource storedResource, Long rangeStart,
			Long rangeEnd) throws Exception {

		StreamRange range = null;
		if (rangeStart != null && rangeEnd != null) {
			range = session.create(StreamRange.T);
			range.setStart(rangeStart);
			range.setEnd(rangeEnd);
		}

		GetBinaryResponse response = GetBinaryResponse.T.create();
		GetBinaryResponse getResponse = processor.get(storedResource, range, response);
		Resource getResource = getResponse.getResource();

		try (InputStream in = getResource.openStream()) {
			byte[] actual = IOTools.slurpBytes(in);
			return actual;
		}
	}

	protected byte[] downloadResourceStream(PersistenceGmSession session, GcpStorageBinaryProcessor processor, Resource storedResource,
			Long rangeStart, Long rangeEnd) throws Exception {

		StreamRange range = null;
		if (rangeStart != null && rangeEnd != null) {
			range = session.create(StreamRange.T);
			range.setStart(rangeStart);
			range.setEnd(rangeEnd);
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		StreamBinaryResponse response = StreamBinaryResponse.T.create();
		StreamBinaryResponse streamResponse = processor.stream(storedResource, () -> out, range, null, response);
		assertThat(streamResponse).isNotNull();

		byte[] actual = out.toByteArray();
		return actual;
	}

}
