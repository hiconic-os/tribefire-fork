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
package com.braintribe.gwt.tribefirejs.client.error;

import java.io.IOException;
import java.io.OutputStream;

public class TfJsOutputStream extends OutputStream{

	@Override
	public void write(byte[] b) throws IOException {
		log(new String(b));
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		log(new String(b, off, len));
	}

	@Override
	public void flush() throws IOException {
		//
	}

	@Override
	public void close() throws IOException {
		//
	}

	@Override
	public void write(int b) throws IOException {
		log(b + "");
	}
	
	private static native void log(String msg) /*-{
		console.log(msg);
	}-*/;
	
}
