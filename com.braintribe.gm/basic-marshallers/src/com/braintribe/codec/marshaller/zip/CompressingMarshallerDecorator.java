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
package com.braintribe.codec.marshaller.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.api.CharacterMarshaller;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.utils.Base64;
import com.braintribe.utils.IOTools;

public class CompressingMarshallerDecorator implements CharacterMarshaller {

	protected CharacterMarshaller embeddedMarshaller;

	@Override
	public void marshall(OutputStream out, Object value) throws MarshallException {
		this.marshall(out, value, GmSerializationOptions.deriveDefaults().build());
	}
	@Override
	public void marshall(OutputStream out, Object value, GmSerializationOptions options) throws MarshallException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			this.embeddedMarshaller.marshall(baos, value, options);
			String base64Encoded = Base64.encodeBytes(baos.toByteArray(), 0, baos.size(), Base64.GZIP);
			out.write(base64Encoded.getBytes("UTF-8"));
		} catch (IOException e) {
			throw new MarshallException("Could not create GZIPOutputStream around the output stream.", e);
		}
	}

	@Override
	public Object unmarshall(InputStream in) throws MarshallException {
		return this.unmarshall(in, GmDeserializationOptions.deriveDefaults().build());
	}
	@Override
	public Object unmarshall(InputStream in, GmDeserializationOptions options) throws MarshallException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOTools.pump(in, baos);
			String decodedString = new String(Base64.decode(baos.toString("UTF-8")), "UTF-8");
			return this.embeddedMarshaller.unmarshall(new ByteArrayInputStream(decodedString.getBytes()), options);
		} catch (IOException e) {
			throw new MarshallException("Could not create GZIPInputStream around the input stream.", e);
		}
	}

	@Override
	public void marshall(Writer writer, Object value, GmSerializationOptions options) throws MarshallException {
		try {
			StringWriter buffer = new StringWriter();
			this.embeddedMarshaller.marshall(buffer, value, options);
			String marshalledRawString = buffer.toString();
			String base64Encoded = Base64.encodeBytes(marshalledRawString.getBytes("UTF-8"), 0, marshalledRawString.length(), Base64.GZIP);
			writer.write(base64Encoded);
		} catch (IOException e) {
			throw new MarshallException("Could not create GZIPOutputStream around the output stream.", e);
		}
	}
	@Override
	public Object unmarshall(Reader reader, GmDeserializationOptions options) throws MarshallException {
		try {
			String encodedString = IOTools.slurp(reader);
			String decodedString = new String(Base64.decode(encodedString), "UTF-8");
			return this.embeddedMarshaller.unmarshall(new StringReader(decodedString), options);
		} catch (IOException e) {
			throw new MarshallException("Could not create GZIPInputStream around the input stream.", e);
		}
	}

	public CharacterMarshaller getEmbeddedMarshaller() {
		return embeddedMarshaller;
	}
	@Required
	@Configurable
	public void setEmbeddedMarshaller(CharacterMarshaller embeddedMarshaller) {
		this.embeddedMarshaller = embeddedMarshaller;
	}
}
