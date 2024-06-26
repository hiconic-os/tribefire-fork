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
package com.braintribe.gwt.fileapi.client;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class FileReader {

	@JsOverlay
	public static final short EMPTY = 0;
	@JsOverlay
	public static final short LOADING = 1;
	@JsOverlay
	public static final short DONE = 2;

	protected FileReader() {

	}

	public String result;
	public short readyState;

	@JsOverlay
	public static FileReader create() {
		return FileApiNativeHelper.fileReaderCreate();
	}

	@JsOverlay
	public final String getStringResult() {
		return result;
	}

	@JsOverlay
	public final short getReadyState() {
		return readyState;
	}

	@JsOverlay
	public final void readAsText(Blob blob) {
		this.readAsText(blob, null);
	}

	public final native void readAsText(Blob blob, String encoding);

	@JsOverlay
	public final void addEventHandler(ProgressListener progressListener, ProgressEventType type) {
		FileApiNativeHelper.fileReaderAddEventHandler(this, progressListener, type);
	}

	@JsOverlay
	public final void addEventHandler(ProgressListener progressListener, ProgressEventType... types) {
		for (ProgressEventType type : types)
			addEventHandler(progressListener, type);
	}

}
