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
package com.braintribe.model.processing.webrpc.server.multipart;

import java.io.IOException;
import java.io.InputStream;

import com.braintribe.model.generic.session.InputStreamProvider;
import com.braintribe.web.multipart.api.PartReader;

public class PartInputStreamProvider  implements InputStreamProvider {
	
	private PartReader partReader;
	
	public PartInputStreamProvider(PartReader partReader) {
		this.partReader = partReader;
	}
	
	@Override
	public InputStream openInputStream() throws IOException {
		return partReader.openTransferEncodingAwareInputStream();
	}
	
}
