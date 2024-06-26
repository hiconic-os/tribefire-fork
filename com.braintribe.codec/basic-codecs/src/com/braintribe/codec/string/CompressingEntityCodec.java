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
package com.braintribe.codec.string;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.string.GzipBase64Codec;

/**
 * Uses any Object/String codec and additionally zips and Base64-encodes the result (and vice versa).
 * 
 */
public class CompressingEntityCodec implements Codec<Object,String> {

	protected Codec<Object,String> embeddedCodec = null;
	protected GzipBase64Codec compressCodec = new GzipBase64Codec();
	

	@Override
	public String encode(Object value) throws CodecException {
		String encodedValue = this.embeddedCodec.encode(value);
		String compressedValue = this.compressCodec.encode(encodedValue);
		return compressedValue;
	}

	@Override
	public Object decode(String encodedValue) throws CodecException {
		String decompressedValue = this.compressCodec.decode(encodedValue);
		Object decodedValue = this.embeddedCodec.decode(decompressedValue);
		return decodedValue;
	}

	@Override
	public Class<Object> getValueClass() {
		return this.embeddedCodec.getValueClass();
	}

	@Configurable @Required
	public void setEmbeddedCodec(Codec<Object, String> embeddedCodec) {
		this.embeddedCodec = embeddedCodec;
	}
	@Configurable
	public void setCompressCodec(GzipBase64Codec compressCodec) {
		this.compressCodec = compressCodec;
	}

}
