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
package tribefire.extension.audit.processing;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.model.generic.reflection.BaseType;

public class ManipulationRecordValueCodec implements Codec<Object, String> {
	
	public static final ManipulationRecordValueCodec INSTANCE = new ManipulationRecordValueCodec();
	
	@Override
	public String encode(Object value) throws CodecException {
		return ManipulationRecordValueEncoder.encode(BaseType.INSTANCE, value);
	}

	@Override
	public Object decode(String encodedValue) throws CodecException {
		return ManipulationRecordValueDecoder.parse(encodedValue);
	}

	@Override
	public Class<Object> getValueClass() {
		return Object.class;
	}

}
