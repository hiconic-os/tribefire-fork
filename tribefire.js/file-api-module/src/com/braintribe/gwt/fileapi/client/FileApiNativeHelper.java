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

import com.google.gwt.dom.client.FormElement;

import jsinterop.annotations.JsOverlay;

/**
 * Since native types cannot have JSNI {@link JsOverlay}s, we implement them here.
 * 
 * @author peter.gazdik
 */
class FileApiNativeHelper {

	public static native Blob blobCreateFromString(String data, String mimeType) /*-{
		return new $wnd.Blob([data],{type: mimeType});
	}-*/;

	public static native String blobUrl(Blob _this) /*-{
		return $wnd.URL.createObjectURL(_this);
	}-*/;

	public static native FileReader fileReaderCreate() /*-{
		return new $wnd.FileReader();
	}-*/;

	public static native void fileReaderAddEventHandler(FileReader _this, ProgressListener progressListener, ProgressEventType type) /*-{
		var slot = type.@java.lang.Enum::name()();
		_this['on' + slot] = $entry(function(event) {
			progressListener.@ProgressListener::onProgress(*)(event, type);
		});
	}-*/;

	public static native FormData formDataCreate() /*-{
		 return new $wnd.FormData();
	}-*/;

	public static native FormData formDataCreate(FormElement form) /*-{
		 return new $wnd.FormData(form);
	}-*/;

	public static final native String progressEventGetErrorMessage(ProgressEvent _this) /*-{
	 	return _this.error.message;
	}-*/;

}
