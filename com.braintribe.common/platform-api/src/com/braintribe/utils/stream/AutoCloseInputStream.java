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
package com.braintribe.utils.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Automatically closes the input stream when EOF has been reached.
 */
public class AutoCloseInputStream extends BasicDelegateInputStream {

	public AutoCloseInputStream(InputStream delegate) {
		super(delegate);
	}

	@Override
	protected void afterRead(final int n) throws IOException {
		if (n == EOF) {
			close();
		}
	}

}
