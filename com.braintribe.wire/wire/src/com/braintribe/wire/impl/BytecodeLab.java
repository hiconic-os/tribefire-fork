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
package com.braintribe.wire.impl;

import java.awt.Button;

import com.braintribe.wire.api.EnrichedWireSpace;
import com.braintribe.wire.api.ImportFieldRecorder;
import com.braintribe.wire.api.annotation.Import;

public class BytecodeLab extends X implements EnrichedWireSpace {
	@Import
	private String field1;
	@Import
	private String field2;
	
	public static void main(String[] args) {
		Runnable r = () -> System.out.println("foobar");
	}
	
	@Override
	public void __listImportFields(ImportFieldRecorder recorder) {
		if (EnrichedWireSpace.class.isAssignableFrom(BytecodeLab.class.getSuperclass()))
			super.__listImportFields(recorder);
		
		recorder.record(BytecodeLab.class, String.class, 0);
		recorder.record(BytecodeLab.class, String.class, 1);
	}

	@Override
	public void __setImportField(Class<?> atClass, int index, Object value) {
		if (BytecodeLab.class != atClass) {
			// super.__setImportField(atClass, index, value);
			return;
		}
		
		switch (index) {
		case 0:
			field1 = (String)value;
			break;
		case 1:
			field2 = (String)value;
			break;
		default:
			throw new IllegalArgumentException("index out of bounds");
		}
	}

}

class X implements EnrichedWireSpace {
	@Import
	private Integer fieldA;
	@Import
	private Integer fieldB;

	
	@Override
	public void __listImportFields(ImportFieldRecorder recorder) {
		if (EnrichedWireSpace.class.isAssignableFrom(X.class.getSuperclass())) {
			// super.__listImportFields(recorder);
		}
		
		recorder.record(X.class, Integer.class, 0);
		recorder.record(X.class, Integer.class, 1);
	}
	
	public void __setImportField_fieldA(Integer value) {
		fieldA = value;
	}
	
	@Override
	public void __setImportField(Class<?> atClass, int index, Object value) {
		if (X.class != atClass) {
			// super.__setImportField(atClass, index, value);
			return;
		}

		switch (index) {
		case 0:
			fieldA = (Integer)value;
			break;
		case 1:
			fieldB = (Integer)value;
			break;
		default:
			throw new IllegalArgumentException("index out of bounds");
		}

	}
}

