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
package com.braintribe.utils.stream.tracking;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

import org.junit.Test;

import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.tracking.data.StreamStatistics;

public class StreamTrackingTests {

	@Test
	public void testInputStreamTracking() throws Exception {
		InputStreamTracker tracker = new InputStreamTracker();

		String expected = "Hello, world!";

		ByteArrayInputStream bain = new ByteArrayInputStream(expected.getBytes(StandardCharsets.UTF_8));
		try (InputStream wrapped = tracker.wrapInputStream(bain, "test", "localhost", "some meaningful context")) {
			String actual = IOTools.slurp(wrapped, "UTF-8");
			assertThat(actual).isEqualTo(expected);
		}

		TreeMap<String, StreamStatistics> stats = tracker.getStatistics();
		assertThat(stats).hasSize(1);
		StreamStatistics ss = stats.get("test");
		assertThat(ss.getTotalStreamsOpened()).isEqualTo(1);
		assertThat(ss.getStreamsFullyTransferred()).isEqualTo(1);
		assertThat(ss.getStreamsPartiallyTransferred()).isEqualTo(0);
		assertThat(ss.getTotalBytesTransferred()).isEqualTo(expected.getBytes(StandardCharsets.UTF_8).length);

	}

	@Test
	public void testOutputStreamTracking() throws Exception {

		OutputStreamTracker tracker = new OutputStreamTracker();
		String expected = "Hello, world!";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (OutputStream wrapped = tracker.wrapOutputStream(baos, "test", "localhost", "some meaningful context")) {
			wrapped.write(expected.getBytes(StandardCharsets.UTF_8));
		}

		TreeMap<String, StreamStatistics> stats = tracker.getStatistics();
		assertThat(stats).hasSize(1);
		StreamStatistics ss = stats.get("test");
		assertThat(ss.getTotalStreamsOpened()).isEqualTo(1);
		assertThat(ss.getStreamsFullyTransferred()).isEqualTo(1);
		assertThat(ss.getStreamsPartiallyTransferred()).isEqualTo(0);
		assertThat(ss.getTotalBytesTransferred()).isEqualTo(expected.getBytes(StandardCharsets.UTF_8).length);

	}

}
