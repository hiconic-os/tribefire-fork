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
package com.braintribe.devrock.mc.core.artifactindex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.braintribe.devrock.mc.core.repository.index.ArtifactIndex;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.WriterOutputStream;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipes;

public class ArtifactIndexTest {
	@Test
	public void testUpdate() throws IOException {
		ArtifactIndex index = new ArtifactIndex(true);
		
		index.update("foo.bar:parent#1.0.1");
		index.update("foo.bar:x#1.0.1");
		
		StreamPipe pipe = StreamPipes.simpleFactory().newPipe("artifact-index-test");
		
		try (OutputStream out = pipe.openOutputStream()) {
			index.write(out);
		}
		

		final ArtifactIndex index2;
		try (InputStream in = pipe.openInputStream()) {
			index2 = ArtifactIndex.read(in, true);
		}
		
		//index2.write(System.out);
		
		index2.update("foo.bar:parent#1.0.2");
		index2.update("foo.bar:x#1.0.2");

		StringWriter stringWriter = new StringWriter();
		try (OutputStream out = new WriterOutputStream(stringWriter, "UTF-8")) {
			index2.write(out);
		}
		
		String content = stringWriter.toString();
		String lines[] = content.split("\\n");
		
		
		Assertions.assertThat(lines).isEqualTo(new String[] {
				"3 foo.bar:x#1.0.2 U", 
				"2 foo.bar:parent#1.0.2 U", 
				"1 foo.bar:x#1.0.1 U", 
				"0 foo.bar:parent#1.0.1 U"
		});
	}
}
