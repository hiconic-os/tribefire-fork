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
package com.braintribe.utils.stream.tracking.stream;

import java.io.IOException;
import java.io.OutputStream;

import com.braintribe.utils.stream.BasicDelegateOutputStream;
import com.braintribe.utils.stream.tracking.OutputStreamTracker;
import com.braintribe.utils.stream.tracking.data.StreamInformation;

public class TrackedOutputStream extends BasicDelegateOutputStream {

	private OutputStreamTracker tracker;
	private StreamInformation info;

	public TrackedOutputStream(OutputStreamTracker tracker, StreamInformation info, OutputStream delegate) {
		super(delegate);
		this.tracker = tracker;
		this.info = info;
	}

	@Override
	public void write(int b) throws IOException {
		super.write(b);
		info.bytesTransferred++;
	}

	@Override
	public void write(byte[] b) throws IOException {
		super.write(b);
		info.bytesTransferred += b.length;
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		super.write(b, off, len);
		info.bytesTransferred += len;
	}

	@Override
	public void close() throws IOException {
		try {
			super.close();
		} finally {
			if (info.closed()) {
				info.eofReached();
				tracker.streamClosed(info);
			}
		}
	}

	public StreamInformation getStreamInformation() {
		return info;
	}
}
