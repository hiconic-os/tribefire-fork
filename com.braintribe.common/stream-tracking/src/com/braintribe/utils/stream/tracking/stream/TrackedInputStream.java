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
import java.io.InputStream;

import com.braintribe.utils.stream.BasicDelegateInputStream;
import com.braintribe.utils.stream.tracking.InputStreamTracker;
import com.braintribe.utils.stream.tracking.data.StreamInformation;

public class TrackedInputStream extends BasicDelegateInputStream {

	private InputStreamTracker tracker;
	private StreamInformation info;

	public TrackedInputStream(InputStreamTracker tracker, StreamInformation info, InputStream delegate) {
		super(delegate);
		this.tracker = tracker;
		this.info = info;
	}

	@Override
	protected void afterRead(int n) throws IOException {
		if (n == EOF) {
			info.eofReached();
			tracker.eofReached(info);
		} else {
			info.bytesTransferred += n;
		}
	}
	@Override
	public void close() throws IOException {
		try {
			super.close();
		} finally {
			if (info.closed()) {
				tracker.streamClosed(info);
			}
		}
	}

	public StreamInformation getStreamInformation() {
		return info;
	}
}
