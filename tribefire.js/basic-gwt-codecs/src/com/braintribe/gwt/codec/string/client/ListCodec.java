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
package com.braintribe.gwt.codec.string.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.codec.Codec;

public class ListCodec<T> extends AbstractCollectionCodec<List<T>, T> {
	@SuppressWarnings({ "cast", "rawtypes" })
	public ListCodec(Codec<T, String> elementCodec) {
		super((Class<List<T>>)(Class) List.class, elementCodec);
	}
	
	@SuppressWarnings({ "cast", "rawtypes" })
	public ListCodec() {
		super((Class<List<T>>)(Class) List.class);
	}
	
	@Override
	protected List<T> createCollection() {
		return new ArrayList<T>();
	}
	
}
