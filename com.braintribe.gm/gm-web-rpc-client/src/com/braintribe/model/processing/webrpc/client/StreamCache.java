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
package com.braintribe.model.processing.webrpc.client;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.common.lcd.Pair;
import com.braintribe.model.resource.CallStreamCapture;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipeFactory;

class StreamCache implements AutoCloseable {
	private final Map<String, CallStreamCapture> callStreamCaptures;
	private final Map<String, OutputStream> captureStreams = new HashMap<>();
	private final Map<String, StreamPipe> pipes = new HashMap<>();
	private final StreamPipeFactory streamPipeFactory;

	public StreamCache(Map<String, CallStreamCapture> callStreamCaptures, StreamPipeFactory streamPipeFactory) {
		super();
		this.callStreamCaptures = callStreamCaptures;
		this.streamPipeFactory = streamPipeFactory;
	}

	public StreamPipe acquirePipe(String bindId) {
		return pipes.computeIfAbsent(bindId, id -> streamPipeFactory.newPipe("GmWebRpcClient-stream-part-" + id));
	}

	public Pair<OutputStream, Boolean> acquireStream(String bindId) {
		OutputStream out = captureStreams.get(bindId);
		boolean fresh = false;
		if (out == null) {
			fresh = true;
			CallStreamCapture callStreamCapture = callStreamCaptures.get(bindId);

			if (callStreamCapture != null) {
				out = callStreamCapture.openStream();
			} else {
				StreamPipe pipe = acquirePipe(bindId);
				out = pipe.openOutputStream();
			}
			captureStreams.put(bindId, out);
		}

		return Pair.of(out, fresh);
	}

	@Override
	public void close() {
		for (OutputStream out : captureStreams.values()) {
			try {
				out.close();
			} catch (Exception e) {
				GmWebRpcClientBase.logger.error("error while closing stream: " + out, e);
			}
		}
	}

	public void checkPipeSatisfaction() {
		List<String> unsatisfiedPipes = null;

		for (Map.Entry<String, StreamPipe> entry : pipes.entrySet()) {
			if (!entry.getValue().wasOutputStreamOpened()) {
				if (unsatisfiedPipes == null) {
					unsatisfiedPipes = new ArrayList<>();
				}
				unsatisfiedPipes.add(entry.getKey());
			}
		}

		if (unsatisfiedPipes != null)
			throw new IllegalStateException("Missing data for instances of TransientSource with the following globalIds: " + unsatisfiedPipes);
	}
}