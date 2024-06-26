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
package com.braintribe.web.multipart.streams;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import com.braintribe.utils.IOTools;
import com.braintribe.web.multipart.impl.RemainingBufferAccess;

public abstract class AbstractPartInputStream extends InputStream implements RemainingBufferAccess {
	private Consumer<? super AbstractPartInputStream> closeHandler;

	public void setCloseHandler(Consumer<? super AbstractPartInputStream> closeHandler) {
		this.closeHandler = closeHandler;
	}

	@Override
	public void close() throws IOException {
		byte[] buffer = new byte[IOTools.SIZE_8K];
		while (read(buffer) != -1) {
			// Consume the rest of the part and ignore its content
		}

		if (closeHandler != null) {
			closeHandler.accept(this);
		}

		closeHandler = null;
	}

}
