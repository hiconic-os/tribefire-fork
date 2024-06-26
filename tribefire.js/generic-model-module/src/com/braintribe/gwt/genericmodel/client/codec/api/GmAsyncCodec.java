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
package com.braintribe.gwt.genericmodel.client.codec.api;

import com.braintribe.codec.CodecException;
import com.braintribe.gwt.async.client.Future;

public interface GmAsyncCodec<T, E> {
	public <T1 extends T> T1 decode(E encodedValue, GmDecodingContext context) throws CodecException;
	public <T1 extends T> Future<T1> decodeAsync(E encodedValue, GmDecodingContext context);
	public E encode(T value, GmEncodingContext context) throws CodecException;
	public Future<E> encodeAsync(T value, GmEncodingContext context);
}
