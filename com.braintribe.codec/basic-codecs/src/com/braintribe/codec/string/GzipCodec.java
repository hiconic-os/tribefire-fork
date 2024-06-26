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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.logging.Logger;
import com.braintribe.utils.IOTools;

public class GzipCodec implements Codec<byte[],byte[]> {

	protected static Logger logger = Logger.getLogger(GzipCodec.class);
	
	@Override
	public byte[] encode(byte[] value) throws CodecException {
		if (value == null) {
			return null;
		}
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(value.length);
		GZIPOutputStream zipStream = null;
		try {
			zipStream = new GZIPOutputStream(byteStream);
			zipStream.write(value);
		} catch(Exception e) {
			throw new CodecException("Could not GZIP byte array.", e);
		} finally {
			IOTools.closeCloseable(zipStream, logger);
			IOTools.closeCloseable(byteStream, logger);
		}

		byte[] compressedData = byteStream.toByteArray();
		return compressedData;
	}

	@Override
	public byte[] decode(byte[] encodedValue) throws CodecException {
		if (encodedValue == null) {
			return null;
		}

		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(encodedValue);
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		GZIPInputStream unzipStream = null;
		
		try {
			unzipStream = new GZIPInputStream(byteInputStream);
			IOTools.pump(unzipStream, byteOutputStream);
			
		} catch(Exception e) {
			throw new CodecException("Could not GUNZIP byte array.", e);
		} finally {
			IOTools.closeCloseable(unzipStream, logger);
			IOTools.closeCloseable(byteInputStream, logger);
			IOTools.closeCloseable(byteOutputStream, logger);
		}

		byte[] uncompressedData = byteOutputStream.toByteArray();
		return uncompressedData;
	}

	@Override
	public Class<byte[]> getValueClass() {
		return byte[].class;
	}

}
