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
package com.braintribe.devrock.mc.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.AbstractHttpEntity;

import com.braintribe.model.generic.session.OutputStreamer;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipes;

public class OutputStreamerEntity extends AbstractHttpEntity {

	private OutputStreamer outputStreamer;
	
	public OutputStreamerEntity(OutputStreamer outputStreamer) {
		super();
		this.outputStreamer = outputStreamer;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public long getContentLength() {
		return -1;
	}

	@Override
	public InputStream getContent() throws IOException, UnsupportedOperationException {
		StreamPipe pipe = StreamPipes.fileBackedFactory().newPipe("http-output");
		
		try (OutputStream out = pipe.openOutputStream()) {
			writeTo(out);
		}
		
		return pipe.openInputStream();
	}

	@Override
	public void writeTo(OutputStream outStream) throws IOException {
		outputStreamer.writeTo(outStream);
	}

	@Override
	public boolean isStreaming() {
		return false;
	}
}
