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
package com.braintribe.gwt.browserfeatures.client;

/**
 * Wrapping the HTML 5 WebSocket API using GWT's JsInterop
 *
 */
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.typedarrays.shared.ArrayBuffer;
import com.google.gwt.typedarrays.shared.ArrayBufferView;
import com.google.gwt.user.client.Event;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * Simple JSO wrapper the WebSocket object.
 *
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
@SuppressWarnings("unusable-by-js")
public class WebSocket {

    public static int CLOSED;
    public static int CLOSING;
    public static int CONNECTING;
    public static int OPEN;

    public String binaryType;
    public int bufferedAmount;
    public OnCloseCallback onclose;
    @SuppressWarnings("rawtypes")
	public OnMessageCallback onmessage;
    public OnOpenCallback onopen;
    public OnErrorCallback onerror;
    public int readyState;
    public String url;

    /**
     * @param url - URL of the WS in the services.
     */
    public WebSocket(String url) {
    	//NOP
    }

    public native boolean send(ArrayBufferView data);

    public native boolean send(String data);

    public native boolean send(ArrayBuffer data);

	public native void close();

    @JsFunction
    public interface OnCloseCallback {
        void onClose(Event a);

    }
    @JsFunction
    public interface OnMessageCallback<T> {
        void onMessage(MessageEvent<T> a);

    }
    @JsFunction
    public interface OnOpenCallback {
        void onOpen(Event a);
    }

    @JsFunction
    public interface OnErrorCallback {
        void onError(JavaScriptObject error);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL)
    public static class MessageEvent<T> {
        public T data;
    }
}
